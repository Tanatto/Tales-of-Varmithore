package app.mathnek.talesofvarmithore.entity.pupfish;

import app.mathnek.talesofvarmithore.blocks.ToVBlocks;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PupfishEntity extends TamableAnimal implements IAnimatable {

    @javax.annotation.Nullable
    private PupfishEntity.PupfishAvoidEntityGoal<Player> pupfishAvoidPlayersGoal;

    private AnimationFactory factory = new AnimationFactory(this);

    protected static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(PupfishEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> COMMANDS =
            SynchedEntityData.defineId(PupfishEntity.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> VARIANTS =
            SynchedEntityData.defineId(PupfishEntity.class, EntityDataSerializers.INT);


    public PupfishEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new PupfishEntity.PupfishMoveControl(this);
        this.lookControl = new PupfishEntity.PupfishLookControl(this, 20);
        this.maxUpStep = 1.0F;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("pupfish.Walk", true));
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("pupfish.Sit", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("pupfish.swim", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving() && this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("pupfish.HoverInWater", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("pupfish.Idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (!isTame() && isBaby() && !isCommandItem(itemstack)) {
            if (!level.isClientSide() && itemForTaming(itemstack) && !isTame() && !ForgeEventFactory.onAnimalTame(this, player)) {
                itemstack.shrink(1);
                tamedFor(player, getRandom().nextInt(5) == 0);
                this.level.broadcastEntityEvent(this, (byte) 7);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        if (isCommandItem(itemstack) && isOwnedBy(player) && this.isTame()) {
            modifyCommand(2, player);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.is(Items.STICK);
    }

    public boolean itemForTaming(ItemStack stack) {
        return stack.is(Item.byBlock(ToVBlocks.PERSILA.get())) && stack.is(Item.byBlock(ToVBlocks.UNCIA.get()));
    }

    public boolean itemForBreeding(ItemStack stack) {
        return stack.is(ItemTags.FISHES);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setIsSitting(tag.getBoolean("isSitting"));
        setVariant(tag.getInt("variant"));
        setCommands(tag.getInt("commands"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isEntitySitting());
        tag.putInt("variant", this.getVariant());
        tag.putInt("commands", this.getCommand());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(VARIANTS, 0);
        this.entityData.define(COMMANDS, 0);
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

    protected void registerGoals() {
        this.goalSelector.addGoal(7, new LeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(9, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 0.8D, 50));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(4, new PupfishEntity.PupfishAvoidEntityGoal(this, Player.class, 10F, 1.0D, 1.7D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.5D);
    }

    public boolean shouldStopMovingIndependently() {
        return this.isEntitySitting() && !this.isVehicle();
    }

    public int getCommand() {
        return (Integer) this.entityData.get(COMMANDS);
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

    public int getVariant() {
        return (Integer) this.entityData.get(VARIANTS);
    }

    public void setVariant(int pType) {
        this.entityData.set(VARIANTS, pType);
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        }
        return pSpawnData;
    }

    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ToVEntityTypes.PUPFISH.get().create(pLevel);
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
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return 0.0F;
    }

    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (!this.isNoAi()) {
            this.handleAirSupply(i);
        }

    }

    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && !this.isInWaterRainOrBubble()) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DRY_OUT, 2.0F);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }

    }

    public void rehydrate() {
        int i = this.getAirSupply() + 1800;
        this.setAirSupply(Math.min(i, this.getMaxAirSupply()));
    }

    public int getMaxAirSupply() {
        return 6000;
    }

    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return 1.5D + (double) pEntity.getBbWidth() * 2.0D;
    }

    public boolean canBeLeashed(Player pPlayer) {
        return true;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new PupfishEntity.PupfishPathNavigation(this, pLevel);
    }

    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = pEntity.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, pEntity);
            this.playSound(SoundEvents.AXOLOTL_ATTACK, 1.0F, 1.0F);
        }

        return flag;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        float f = this.getHealth();
        if (!this.level.isClientSide && !this.isNoAi() && this.level.random.nextInt(3) == 0 && ((float) this.level.random.nextInt(3) < pAmount || f / this.getMaxHealth() < 0.5F) && pAmount < f && this.isInWater() && (pSource.getEntity() != null || pSource.getDirectEntity() != null)) {
            this.brain.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, 200);
        }

        return super.hurt(pSource, pAmount);
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    static class PupfishMoveControl extends SmoothSwimmingMoveControl {
        private final PupfishEntity axolotl;

        public PupfishMoveControl(PupfishEntity pAxolotl) {
            super(pAxolotl, 85, 10, 0.1F, 0.5F, false);
            this.axolotl = pAxolotl;
        }

        public void tick() {
            if (this.axolotl.isAlive()) {
                super.tick();
            }

        }
    }

    static class PupfishPathNavigation extends WaterBoundPathNavigation {
        PupfishPathNavigation(PupfishEntity pPupfish, Level pLevel) {
            super(pPupfish, pLevel);
        }

        /**
         * If on ground or swimming and can swim
         */
        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int pMaxVisitedNodes) {
            this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
            return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
        }

        public boolean isStableDestination(BlockPos pPos) {
            return !this.level.getBlockState(pPos.below()).isAir();
        }
    }

    class PupfishLookControl extends SmoothSwimmingLookControl {
        public PupfishLookControl(PupfishEntity pPupfish, int pMaxYRotFromCenter) {
            super(pPupfish, pMaxYRotFromCenter);
        }

        /**
         * Updates look
         */
        public void tick() {
            if (PupfishEntity.this.isAlive()) {
                super.tick();
            }

        }
    }

    static class PupfishAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final PupfishEntity ocelot;

        public PupfishAvoidEntityGoal(PupfishEntity pOcelot, Class<T> pEntityClassToAvoid, float pMaxDist, double pWalkSpeedModifier, double pSprintSpeedModifier) {
            super(pOcelot, pEntityClassToAvoid, pMaxDist, pWalkSpeedModifier, pSprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.ocelot = pOcelot;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return super.canUse();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }
    }
}
