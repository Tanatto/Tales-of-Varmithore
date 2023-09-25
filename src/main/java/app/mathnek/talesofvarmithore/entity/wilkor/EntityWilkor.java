package app.mathnek.talesofvarmithore.entity.wilkor;

import app.mathnek.talesofvarmithore.entity.ai.ToVAIWander;
import app.mathnek.talesofvarmithore.entity.ai.ToVAIWatchClosest;
import app.mathnek.talesofvarmithore.entity.ai.ToVFollowParentGoal;
import app.mathnek.talesofvarmithore.entity.ai.ToVRandomLookAroundGoal;
import app.mathnek.talesofvarmithore.entity.ai.wilkor.WilkorAIWander;
import app.mathnek.talesofvarmithore.entity.ai.wilkor.WilkorAIWatchClosest;
import app.mathnek.talesofvarmithore.entity.ai.wilkor.WilkorFollowParentGoal;
import app.mathnek.talesofvarmithore.entity.ai.wilkor.WilkorRandomLookAroundGoal;
import app.mathnek.talesofvarmithore.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import java.util.Optional;

public class EntityWilkor extends TamableAnimal implements IAnimatable {
    protected static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> COMMANDS =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> VARIANTS =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> DISTURB_TICKS =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> SLEEPING =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> ON_GROUND =
            SynchedEntityData.defineId(EntityWilkor.class, EntityDataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public EntityWilkor(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.maxUpStep = 1f;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.walk", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isVehicle()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.run", true));
            return PlayState.CONTINUE;
        }
        if (this.isEntitySitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.sit", true));
            return PlayState.CONTINUE;
        }
        if (this.isEntitySleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.sleep", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.swim", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.idle", true));
        return PlayState.CONTINUE;
    }

    public static AttributeSupplier.Builder setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.JUMP_STRENGTH, 2);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new WilkorFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WilkorAIWander(this, 0.7, 20));
        this.goalSelector.addGoal(7, new WilkorAIWatchClosest(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new WilkorRandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(VARIANTS, 0);
        this.entityData.define(COMMANDS, 0);
        this.entityData.define(DISTURB_TICKS, 0);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(ON_GROUND, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setIsSitting(tag.getBoolean("isSitting"));
        setVariant(tag.getInt("variant"));
        setCommands(tag.getInt("commands"));
        setIsSleeping(tag.getBoolean("sleeping"));
        setSleepDisturbTicks(tag.getInt("disturb_ticks"));
        setIsOnGround(tag.getBoolean("OnGround"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isEntitySitting());
        tag.putInt("variant", this.getVariant());
        tag.putInt("commands", this.getCommand());
        tag.putBoolean("sleeping", this.isEntitySleeping());
        tag.putInt("disturb_ticks", this.getSleepDisturbTicks());
        tag.putBoolean("OnGround", this.isEntityOnGround());
    }

    public boolean shouldStopMovingIndependently() {
        return this.isEntitySleeping() || this.isEntitySitting() && !this.isVehicle();
    }

    public int getCommand() {
        return this.entityData.get(COMMANDS);
    }

    public void setCommands(int commands) {
        this.entityData.set(COMMANDS, commands);
    }

    public void modifyCommand(int limit, Player player) {
        if (this.getCommand() >= limit) {
            this.setCommands(0);
        } else {
            this.setCommands(this.getCommand() + 1);
        }
    }

    public boolean isEntityWandering() {
        return (Integer) this.entityData.get(COMMANDS) == 0;
    }

    public boolean isEntitySitting() {
        return (Integer) this.entityData.get(COMMANDS) == 1;
    }

    public void setIsWandering(boolean wandering) {
        if (wandering) {
            this.entityData.set(COMMANDS, 0);
        }
    }

    public void setIsSitting(boolean sitting) {
        if (sitting) {
            this.entityData.set(COMMANDS, 1);
        }
    }

    public boolean isEntitySleeping() {
        return (Boolean) this.entityData.get(SLEEPING);
    }

    public void setIsSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public boolean isEntityOnGround() {
        return (Boolean) this.entityData.get(ON_GROUND);
    }

    public void setIsOnGround(boolean sleeping) {
        this.entityData.set(ON_GROUND, sleeping);
    }

    public int getVariant() {
        return (Integer) this.entityData.get(VARIANTS);
    }

    public void setVariant(int pType) {
        this.entityData.set(VARIANTS, pType);
    }

    public int getSleepDisturbTicks() {
        return this.entityData.get(DISTURB_TICKS);
    }

    public void setSleepDisturbTicks(int disturbTicks) {
        this.entityData.set(DISTURB_TICKS, disturbTicks);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (isCommandItem(itemstack) && isOwnedBy(player) && this.isTame()) {
            modifyCommand(2, player);
            return InteractionResult.SUCCESS;
        }

        if (!isTame()) {
            if (isItemStackForTaming(itemstack)) {
                this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
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
        }

        if (!isCommandItem(itemstack) && !isFood(itemstack) && !isBaby()) {
            rideInteract(player, hand, itemstack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        this.setSleepDisturbTicks(Util.minutesToSeconds(4));
        return super.mobInteract(player, hand);
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
    @Override
    public void positionRider(@NotNull Entity passenger) {
        Entity riddenByEntity = getControllingPassenger();
        if (riddenByEntity != null) {
            Vec3 pos = new Vec3(0, getPassengersRidingOffset() + riddenByEntity.getMyRidingOffset() + 0.4, /*getScale() + */ +0.55)
                    .yRot((float) Math.toRadians(-yBodyRot))
                    .add(position());
            passenger.setPos(pos.x, pos.y, pos.z);

            // fix rider rotation
            if (getFirstPassenger() instanceof LivingEntity) {
                LivingEntity rider = ((LivingEntity) riddenByEntity);
                rider.xRotO = rider.getXRot();
                rider.yRotO = rider.getYRot();
                rider.yBodyRot = yBodyRot;
            }
        }
    }
    public boolean canBeMounted() {
        return true;
    }

    protected void doPlayerRide(Player pPlayer) {
        if (canBeMounted()) {
            if (!this.level.isClientSide) {
                if (isTame()) {
                    pPlayer.setYRot(this.getYRot());
                    pPlayer.setXRot(this.getXRot());
                }
                if (!pPlayer.isCreative() && isTame()) {
                }
                pPlayer.startRiding(this);
            }

            if (isEntitySitting())
                this.setIsWandering(true);
            if (isEntitySleeping())
                setIsSleeping(false);
            if (getTarget() != null)
                setTarget(null);
        }
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.is(Items.STICK);
    }

    protected boolean isItemStackForTaming(ItemStack stack) {
        return stack.is(Items.BONE);
    }

    public void tamedFor(Player player, boolean successful) {
        if (successful) {
            setTame(true);
            navigation.stop();
            setTarget(null);
            setOwnerUUID(player.getUUID());
            level.broadcastEntityEvent(this, (byte) 7);
        } else {
            level.broadcastEntityEvent(this, (byte) 6);
        }
    }

    public boolean isTamedFor(Player player) {
        return isTame() && isOwnedBy(player);
    }

    public InteractionResult checkAndHandleImportantInteractions(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.LEAD) && this.canBeLeashed(pPlayer)) {
            this.setLeashedTo(pPlayer, true);
            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            if (itemstack.is(Items.NAME_TAG) && isOwnedBy(pPlayer)) {
                InteractionResult interactionresult = itemstack.interactLivingEntity(pPlayer, this, pHand);
                if (interactionresult.consumesAction()) {
                    return interactionresult;
                }
            }

            if (itemstack.getItem() instanceof SpawnEggItem) {
                if (this.level instanceof ServerLevel) {
                    SpawnEggItem spawneggitem = (SpawnEggItem) itemstack.getItem();
                    Optional<Mob> optional = spawneggitem.spawnOffspringFromSpawnEgg(pPlayer, this, (EntityType<? extends Mob>) this.getType(), (ServerLevel) this.level, this.position(), itemstack);
                    optional.ifPresent((p_21476_) -> {
                        this.onOffspringSpawnedFromEgg(pPlayer, p_21476_);
                    });
                    return optional.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
                } else {
                    return InteractionResult.CONSUME;
                }
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
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
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double) f, pTravelVector.y, (double) f1));
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

        if (this.isEntitySitting()) {
            this.setOrderedToSit(true);
        }

        if (!this.isEntitySitting()) {
            this.setOrderedToSit(false);
        }

        if (this.isEntitySleeping()) {
            this.setIsSitting(false);
        }

        if (this.shouldStopMovingIndependently()) {
            this.getNavigation().stop();
            this.getNavigation().timeoutPath();
            this.setRot(this.getYRot(), this.getXRot());
        }

        if (this.getSleepDisturbTicks() > 0) {
            this.setSleepDisturbTicks(this.getSleepDisturbTicks() - 1);
        }
        this.sleepMechanics();
    }

    public boolean isNocturnal() {
        return false;
    }

    protected void sleepMechanics() {
        if (!this.level.isClientSide()) {
            if (this.canSleep()) {
                if (!this.isNocturnal()) {
                    if (!this.level.isDay()) {
                        this.setIsSleeping(true);
                    } else {
                        this.setIsSleeping(false);
                    }
                } else if (this.level.isDay()) {
                    this.setIsSleeping(true);
                } else {
                    this.setIsSleeping(false);
                }
            } else {
                this.setIsSleeping(false);
            }
        }
    }

    public boolean canSleep() {
        LivingEntity lastHurt = this.getLastHurtByMob();
        LivingEntity target = this.getTarget();
        boolean canSleep = lastHurt == null && target == null;

        if (!canSleep) {
            return false;
        }

        if (getOwner() instanceof Mob mob) {
            if (mob.getTarget() != null) {
                return false;
            } else {
                return true;
            }
        }

        if (this.isVehicle()) {
            return false;
        }

        if (this.getVehicle() != null) {
            return false;
        }

        if (isEntitySitting() || isEntityWandering()) {
            return true;
        }

        if (getVehicle() instanceof Player) {
            return false;
        }

        return this.isEntityOnGround();
    }

    @Override
    public boolean canBeControlledByRider() {
        return getControllingPassenger() instanceof LivingEntity driver && isOwnedBy(driver);
    }

    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    public void setRidingPlayer(Player player) {
        player.setYRot(getYRot());
        player.setXRot(getXRot());
        player.startRiding(this);
    }

    public float getSoundPitch() {
        return this.isBaby() ? 1.4F : 1.0F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}