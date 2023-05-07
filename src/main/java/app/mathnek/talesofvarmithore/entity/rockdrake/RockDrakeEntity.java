package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class RockDrakeEntity extends TamableAnimal implements IAnimatable, Saddleable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> VARIANTS =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);

    public RockDrakeEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setVariant(this.random.nextInt(this.getMaxAmountOfVariants()));
        return pSpawnData;
    }

    public int getMaxAmountOfVariants() {
        return 13;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.walk", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isVehicle()) {
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

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSitting(tag.getBoolean("isSitting"));
        setVariant(tag.getInt("variant"));
        setSaddled(tag.getBoolean("Saddled"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isSitting());
        tag.putInt("variant", this.getVariant());
        tag.putBoolean("Saddled", this.isSaddled());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(VARIANTS, 0);
        this.entityData.define(SADDLED, false);
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

        if (!isBaby() && item == Items.COOKIE) {
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
    public void tick() {
        super.tick();

        if (this.shouldStopMovingIndependently()) {
            this.getNavigation().stop();
            this.getNavigation().timeoutPath();
            this.setRot(this.getYRot(), this.getXRot());
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return ToVEntityTypes.ROCKDRAKE.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        /*data.addAnimationController(new AnimationController(this, "attack_Controller",
                0, this::attackController));*/
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}
