package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.network.ControlMessageBite;
import app.mathnek.talesofvarmithore.network.ControlNetwork;
import app.mathnek.talesofvarmithore.util.MathB;
import app.mathnek.talesofvarmithore.util.ToVKeybinds;
import app.mathnek.talesofvarmithore.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Random;

public class RockDrakeEntity extends TamableAnimal implements IAnimatable, Saddleable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> BITE =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> VARIANTS =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> CURRENT_ATTACK =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_BITE =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> TICKS_SINCE_LAST_ATTACK =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);

    private static final String NBT_SADDLED = "Saddle";
    protected int ticksSinceLastBiteAttack = 0;

    public RockDrakeEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.5f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SitWhenOrderedToGoal(this));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return ToVEntityTypes.ROCKDRAKE.get().create(serverLevel);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isTame()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.walk", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isTame()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.run", true));
            return PlayState.CONTINUE;
        }
        if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.sit", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            /*if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.bite", true));
                return PlayState.CONTINUE;
            }*/
        }
        if(isBiting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.bite", true));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attack_Controller",
                0, this::attackController));
    }

    public boolean doHurtTarget(Entity pEntity) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (pEntity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
            f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }

        boolean flag = pEntity.hurt(DamageSource.mobAttack(this), pEntity instanceof Player player && player.getArmorValue() > 0 ? f / 2 : f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                ((LivingEntity) pEntity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }


        return flag;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        float yRotRadians = MathB.toRadians(this.getYRot());
        float sinY = Mth.sin(yRotRadians);
        float cosY = Mth.cos(yRotRadians);

        if (isBiting() && getTicksSincePlayerLastBiteAttack(Util.secondsToTicks(1)) == 0) {
            ticksSinceLastBiteAttack = 20;
        } else {
            if (ticksSinceLastBiteAttack > 0) {
                ticksSinceLastBiteAttack -= 1;
            } else {
                ticksSinceLastBiteAttack = 0;
            }
        }

        if (ticksSinceLastBiteAttack == 18) {
            this.getTicksSincePlayerLastBiteAttack(Util.secondsToTicks(1));
        }
    }

    @Override
    public void swing(InteractionHand pHand) {
        Random random = new Random();
        int random1 = random.nextInt(300);
        if (random1 > 1) {
            ticksSinceLastBiteAttack = Util.secondsToTicks(3);
        }

        if (random1 > 120) {
            ticksSinceLastBiteAttack = 0;
        }

        if (random1 > 210) {
            ticksSinceLastBiteAttack = 0;
        }

        super.swing(pHand);
    }



    @OnlyIn(Dist.CLIENT)
    public void updateClientControls() {
        if (ToVKeybinds.BITE.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(true, this.getId()));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(false, this.getId()));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if(isTame() && !this.level.isClientSide && item == Items.STICK) {
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }

        if (isTame() && isSaddleable() && !isSaddled() && itemstack.getItem() instanceof SaddleItem) {
            itemstack.shrink(1);
            equipSaddle(getSoundSource());
            return InteractionResult.SUCCESS;
        }

        if (isTame() && isSaddled()) {
            rideInteract(player, hand, itemstack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        if (isBaby()) {
            if (this.random.nextInt(7) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget((LivingEntity) null);
                this.level.broadcastEntityEvent(this, (byte) 7);
                if (!player.getAbilities().instabuild && !player.getLevel().isClientSide()) {
                    itemstack.shrink(1);
                }
            }
        }

        return super.mobInteract(player, hand);
    }

    protected void doPlayerRide(Player pPlayer) {
        if (canBeMounted()) {
            if (!this.level.isClientSide) {
                if (isTame()) {
                    pPlayer.setYRot(this.getYRot());
                    pPlayer.setXRot(this.getXRot());
                }
                if (!isSaddled() && !pPlayer.isCreative() && isTame()) {
                }
                pPlayer.startRiding(this);
            }
            if (getTarget() != null)
                setTarget(null);
        }
    }

    public boolean canBeMounted() {
        return true;
    }

    protected void rideInteract(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        if (isTame()) {
            if (pPlayer == this.getOwner()) {
                this.doPlayerRide(pPlayer);
            } else if (pPlayer != getOwner() && getControllingPassenger() == this.getOwner()) {
                this.doPlayerRide(pPlayer);
            }
        }
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double)f, pTravelVector.y, (double)f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    public boolean canBeControlledByRider() {
        return getControllingPassenger() instanceof LivingEntity driver && isOwnedBy(driver);
    }

    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = getPassengers();
        return list.isEmpty()? null : list.get(0);
    }

    public void setRidingPlayer(Player player) {
        player.setYRot(getYRot());
        player.setXRot(getXRot());
        player.startRiding(this);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setVariant(this.random.nextInt(this.getMaxAmountOfVariants()));
        return pSpawnData;
    }

    public int getMaxAmountOfVariants() {
        return 13;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSitting(tag.getBoolean("isSitting"));
        setVariant(tag.getInt("variant"));
        setIsBiting(tag.getBoolean("bite"));
        setSaddled(tag.getBoolean("Saddled"));
        setCurrentAttackType(tag.getInt("current_attack"));
        setTicksSincePlayerLastBiteAttack(tag.getInt("ticks_since_bite_attack"));
        setTicksSinceLastAttack(tag.getInt("ticks_since_last_attack"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isSitting());
        tag.putInt("variant", this.getVariant());
        tag.putBoolean("ability", this.isBiting());
        tag.putBoolean("Saddled", this.isSaddled());
        tag.putInt("current_attack", this.getCurrentAttackType());
        tag.putInt("ticks_since_bite_attack", getTicksSincePlayerLastBiteAttack(Util.secondsToTicks(1)));
        tag.putInt("ticks_since_last_attack", this.getTicksSinceLastAttack());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(VARIANTS, 0);
        this.entityData.define(BITE, false);
        this.entityData.define(SADDLED, false);
        this.entityData.define(CURRENT_ATTACK, 0);
        this.entityData.define(TICK_SINCE_LAST_BITE, 0);
        this.entityData.define(TICKS_SINCE_LAST_ATTACK, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksSinceLastBiteAttack >= 0) {
            ticksSinceLastBiteAttack--;
        }

         if (ticksSinceLastBiteAttack >= 0) {
            setCurrentAttackType(1);
        }

        if (this.level.isClientSide()) {
            this.updateClientControls();
        }

        if (this.shouldStopMovingIndependently()) {
            this.getNavigation().stop();
            this.getNavigation().timeoutPath();
            this.setRot(this.getYRot(), this.getXRot());
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null && (s.equals("Bluedude") || s.equals("bluedude"))) {
            this.setVariant(14);
        }
    }

    public boolean shouldStopMovingIndependently() {
        return this.isSitting() && !this.isVehicle();
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public int getVariant() {
        return (Integer)this.entityData.get(VARIANTS);
    }

    public void setVariant(int pType) {
        this.entityData.set(VARIANTS, pType);
    }

    public boolean isBiting() {
        return (Boolean)this.entityData.get(BITE);
    }

    public void setIsBiting(boolean ability_pressed) {
        this.entityData.set(BITE, ability_pressed);
    }

    public boolean isSaddled() {
        return entityData.get(SADDLED);
    }

    @Override
    public boolean isSaddleable() {
        return isAlive() && !isBaby() && isTame();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource source) {
        setSaddled(true);
        level.playSound(null, getX(), getY(), getZ(), SoundEvents.HORSE_SADDLE, getSoundSource(), 1, 1);
    }

    public void setSaddled(boolean saddled) {
        entityData.set(SADDLED, saddled);
    }

    public int getCurrentAttackType() {
        return this.entityData.get(CURRENT_ATTACK);
    }

    public void setCurrentAttackType(int currentAttack) {
        this.entityData.set(CURRENT_ATTACK, currentAttack);
    }

    public int getTicksSincePlayerLastBiteAttack(int i) {
        return entityData.get(TICK_SINCE_LAST_BITE);
    }

    public void setTicksSincePlayerLastBiteAttack(int ticksSinceLastBiteAttack) {
        entityData.set(TICK_SINCE_LAST_BITE, ticksSinceLastBiteAttack);
    }

    public int getTicksSinceLastAttack() {
        return (Integer)this.entityData.get(TICKS_SINCE_LAST_ATTACK);
    }

    public void setTicksSinceLastAttack(int ticks) {
        this.entityData.set(TICKS_SINCE_LAST_ATTACK, ticks);
    }
}
