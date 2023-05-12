package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.entity.ai.ToVLookAtPlayerGoal;
import app.mathnek.talesofvarmithore.entity.ai.ToVRandomLookAroundGoal;
import app.mathnek.talesofvarmithore.entity.ai.ToVWaterAvoidingRandomStrollGoal;
import app.mathnek.talesofvarmithore.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseEntityClass extends TamableAnimal implements IAnimatable, Saddleable {
    private AnimationFactory factory = new AnimationFactory(this);

    protected static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> COMMANDS =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> VARIANTS =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> DISTURB_TICKS =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> SLEEPING =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> ON_GROUND =
            SynchedEntityData.defineId(BaseEntityClass.class, EntityDataSerializers.BOOLEAN);

    public BaseEntityClass(EntityType<? extends BaseEntityClass> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setIsSitting(tag.getBoolean("isSitting"));
        setVariant(tag.getInt("variant"));
        setSaddled(tag.getBoolean("Saddled"));
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
        tag.putBoolean("Saddled", this.isSaddled());
        tag.putInt("commands", this.getCommand());
        tag.putBoolean("sleeping", this.isEntitySleeping());
        tag.putInt("disturb_ticks", this.getSleepDisturbTicks());
        tag.putBoolean("OnGround", this.isEntityOnGround());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(VARIANTS, 0);
        this.entityData.define(SADDLED, false);
        this.entityData.define(COMMANDS, 0);
        this.entityData.define(DISTURB_TICKS, 0);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(ON_GROUND, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new ToVLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new ToVRandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new ToVWaterAvoidingRandomStrollGoal(this, 0.7, 1.0000001E-5F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
    }

    public boolean shouldStopMovingIndependently() {
        return this.isEntitySleeping() || this.isEntitySitting() && !this.isVehicle();
    }

    public int getCommand() {
        return (Integer)this.entityData.get(COMMANDS);
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
        return (Integer)this.entityData.get(COMMANDS) == 0;
    }

    public boolean isEntitySitting() {
        return (Integer)this.entityData.get(COMMANDS) == 1;
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
        return (Boolean)this.entityData.get(SLEEPING);
    }

    public void setIsSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public boolean isEntityOnGround() {
        return (Boolean)this.entityData.get(ON_GROUND);
    }

    public void setIsOnGround(boolean sleeping) {
        this.entityData.set(ON_GROUND, sleeping);
    }

    public int getVariant() {
        return (Integer)this.entityData.get(VARIANTS);
    }

    public void setVariant(int pType) {
        this.entityData.set(VARIANTS, pType);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        }
        return pSpawnData;
    }

    public int getMaxAmountOfVariants() {
        return 0;
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

    public int getSleepDisturbTicks() {
        return this.entityData.get(DISTURB_TICKS);
    }

    public void setSleepDisturbTicks(int disturbTicks) {
        this.entityData.set(DISTURB_TICKS, disturbTicks);
    }

    public void aiStep() {
        super.aiStep();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (isCommandItem(itemstack) && isOwnedBy(player) && this.isTame()) {
            modifyCommand(2, player);
            return InteractionResult.SUCCESS;
        }

        if (isTame() && isSaddleable() && !isSaddled() && itemstack.getItem() instanceof SaddleItem) {
            itemstack.shrink(1);
            equipSaddle(getSoundSource());
            return InteractionResult.SUCCESS;
        }

        /*if (isTamedFor(player) && isSaddled() && !isBaby()) {
            if (!level.isClientSide()) {
                setRidingPlayer(player);
                navigation.stop();
                setTarget(null);
            }
            setOrderedToSit(false);
            setInSittingPose(false);
            return InteractionResult.SUCCESS;
        }*/

        if (!isCommandItem(itemstack) && !isFood(itemstack) && !isBaby()) {
            rideInteract(player, hand, itemstack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        this.setSleepDisturbTicks(Util.minutesToSeconds(4));
        return super.mobInteract(player, hand);
    }

    protected void rideInteract(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        if (isTame() && isSaddled()) {
            if (pPlayer == this.getOwner()) {
                this.doPlayerRide(pPlayer);
            } else if (pPlayer != getOwner() && getControllingPassenger() == this.getOwner()) {
                this.doPlayerRide(pPlayer);
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
                if (!isSaddled() && !pPlayer.isCreative() && isTame()) {
                    pPlayer.displayClientMessage(new TranslatableComponent("bdd.dragonAir.needSaddle"), false);
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

    public void tamedFor(Player player, boolean successful) {
        if (successful) {
            setTame(true);
            navigation.stop();
            setTarget(null);
            setOwnerUUID(player.getUUID());
            level.broadcastEntityEvent(this, (byte) 7);
        }
        else {
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

        if (this.isEntitySitting()) {
            this.setOrderedToSit(true);
        }

        if (!this.isEntitySitting()) {
            this.setOrderedToSit(false);
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

    public static class EntityPart extends PartEntity<BaseEntityClass> {

        BaseEntityClass parent;
        EntityDimensions size;
        public final String name;

        public EntityPart(BaseEntityClass parent, String name, float sizeX, float sizeY) {
            super(parent);
            this.size = EntityDimensions.scalable(sizeX, sizeY);
            this.parent = parent;
            this.name = name;
            this.refreshDimensions();
        }

        @Override
        protected void defineSynchedData() {

        }

        @Override
        protected void readAdditionalSaveData(CompoundTag pCompound) {

        }

        @Override
        protected void addAdditionalSaveData(CompoundTag pCompound) {

        }

        @Override
        public boolean isPickable() {
            return true;
        }

        @Override
        public boolean hurt(DamageSource pSource, float pAmount) {
            Entity entity = pSource.getEntity();
            if (entity instanceof LivingEntity rider) {
                if (pSource.equals(DamageSource.mobAttack(rider)) || rider.getVehicle() == parent) {
                    return false;
                }
            }
            return (!isInvulnerableTo(pSource) || Objects.requireNonNull(entity).getVehicle() == parent) && parent.hurt(pSource, pAmount);
        }

        public boolean is(@NotNull Entity pEntity) {
            return this == pEntity || this.parent == pEntity;
        }

        public Packet<?> getAddEntityPacket() {
            throw new UnsupportedOperationException();
        }

        public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
            return this.size;
        }

        public boolean shouldBeSaved() {
            return false;
        }
    }
}
