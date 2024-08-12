package app.mathnek.talesofvarmithore.entity.twintail;

import app.mathnek.talesofvarmithore.entity.EntitySaddleBase;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.messages.ControlMessageBite;
import app.mathnek.talesofvarmithore.messages.ControlNetwork;
import app.mathnek.talesofvarmithore.util.MathB;
import app.mathnek.talesofvarmithore.util.ToVKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class TwinTailEntity extends EntitySaddleBase {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    EntityPart[] subParts;
    EntityPart rockdrakeBiteOffset;

    private static final EntityDataAccessor<Boolean> BITING_DAMAGE =
            SynchedEntityData.defineId(TwinTailEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> BITING =
            SynchedEntityData.defineId(TwinTailEntity.class, EntityDataSerializers.BOOLEAN);

    public TwinTailEntity(EntityType<? extends EntitySaddleBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.rockdrakeBiteOffset = new EntityPart(this, "rockdrakeBiteOffset", 1.5F, 1.5F);
        this.subParts = new EntityPart[]{this.rockdrakeBiteOffset};
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new TwinTailEntity.TwinTailEntityMoveControl(this);
        this.lookControl = new TwinTailEntity.TwinTailEntityLookControl(this, 20);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.5f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.JUMP_STRENGTH, 2);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new TwinTailEntity.TwinTailEntityPathNavigation(this, pLevel);
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 14;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (isDragonMoving() && !shouldStopMovingIndependently() && !isInWater()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("drake.run"));
            } else {
                return event.setAndContinue(RawAnimation.begin().thenLoop("drake.walk"));
            }
        } else if (isDragonMoving() && !shouldStopMovingIndependently() && isInWater()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("drake.swim"));
            } else {
                return event.setAndContinue(RawAnimation.begin().thenLoop("drake.swim"));
            }
        }
        if (this.isEntitySitting()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("drake.sit"));
        }
        if (this.isEntitySleeping()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("drake.sleep"));
        }
        if (event.isMoving() && this.isInWater()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("drake.swim"));
        }

        return event.setAndContinue(RawAnimation.begin().thenLoop("drake.idle"));
    }

    private PlayState attackPredicate(AnimationState event) {
        if (IsBiting()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("drake.bite"));
        }
        return PlayState.CONTINUE;
    }

    // isMoving check that works on servers
    public boolean isDragonMoving() {
        return this.getX() != xOld || this.getZ() != this.zOld;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void rideInteract(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        if (isTame() && isSaddled()) {
            if (pPlayer == this.getOwner()) {
                this.doPlayerRide(pPlayer);
            } else if (pPlayer != getOwner() && getControllingPassenger() == this.getOwner()) {
                this.doPlayerRide(pPlayer);
            }
        }
    }

    /*@Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        Item itemForTaming = Items.BEEF;

        if (!isTame() && isBaby() && !isCommandItem(itemstack)) {
            if (!level.isClientSide() && item == itemForTaming && !isTame()) {
                itemstack.shrink(1);
                tamedFor(player, getRandom().nextInt(5) == 0);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }*/

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        } else {
            this.setVariant(getTypeForBiome(pLevel));
        }
        return pSpawnData;
    }

    @Override
    public void tick() {
        super.tick();

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s.equals("Bluedude") || s.equals("bluedude")) { // TODO: Bluedude?
            this.setVariant(15);
        }

        if (level().isClientSide()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(IsBitingDamageTrue(), this.getId()));
        }

        if (level().isClientSide()) {
            updateClientControls();
        }

        if (IsBiting()) {
            this.setXRot(0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void updateClientControls() {
        if (ToVKeybinds.BITE.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(true, getId()));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(false, getId()));
        }
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subParts;
    }

    private void tickPart(EntityPart pPart, double pOffsetX, double pOffsetY, double pOffsetZ) {
        Vec3 lastPos = new Vec3(pPart.getX(), pPart.getY(), pPart.getZ());
        pPart.setPos(this.getX() + pOffsetX, this.getY() + pOffsetY, this.getZ() + pOffsetZ);

        pPart.xo = lastPos.x;
        pPart.yo = lastPos.y;
        pPart.zo = lastPos.z;
        pPart.xOld = lastPos.x;
        pPart.yOld = lastPos.y;
        pPart.zOld = lastPos.z;

    }

    @Override
    public boolean isMultipartEntity() {
        return !isBaby();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        float yRotRadians = MathB.toRadians(this.getYRot());
        float sinY = Mth.sin(yRotRadians);
        float cosY = Mth.cos(yRotRadians);

        this.tickPart(this.rockdrakeBiteOffset, 3 * -sinY * 1, IsBiting() ? 0.4D : 2D, 3 * cosY * 1);
        Vec3 vec3 = this.getDeltaMovement();
        boolean isMoving = vec3.x > 0 || vec3.y > 0 || vec3.z > 0;
        if (getControllingPassenger() instanceof Player player) {
            if (IsBiting() && IsBitingDamageTrue()) {
                //this.knockBack(this.level.getEntities(this, this.rockdrakeBiteOffset.getBoundingBox().inflate(0.3D, 0.3D, 0.3D).move(0.0D, -0.3D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));

                if (!level().isClientSide()) {
                    this.hurt(this.level().getEntities(this, this.rockdrakeBiteOffset.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                }
            }
        }
    }

    private void hurt(List<Entity> entities) {
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    private int getTypeForBiome(ServerLevelAccessor pLevel) {
        Holder<Biome> biome = pLevel.getBiome(new BlockPos((int)this.position().x, (int)this.position().y, (int)this.position().z));

        if (biome.is(BiomeTags.HAS_IGLOO)) {
            return 14;
        }

        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return ToVEntityTypes.TWINTAIL.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 5, this::movementPredicate));
        data.add(new AnimationController(this, "controller", 5, this::attackPredicate));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BITING_DAMAGE, false);
        this.entityData.define(BITING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsBitingDamageTrue(pCompound.getBoolean("biting_damage"));
        this.setIsBiting(pCompound.getBoolean("biting"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("biting_damage", this.IsBitingDamageTrue());
        pCompound.putBoolean("biting", this.IsBiting());
    }

    public boolean IsBitingDamageTrue() {
        return this.entityData.get(BITING_DAMAGE);
    }

    public void setIsBitingDamageTrue(boolean ram) {
        this.entityData.set(BITING_DAMAGE, ram);
    }

    public boolean IsBiting() {
        return this.entityData.get(BITING);
    }

    public void setIsBiting(boolean ability_pressed) {
        this.entityData.set(BITING, ability_pressed);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public LivingEntity getOwner() {
        return super.getOwner();
    }

    static class TwinTailEntityMoveControl extends SmoothSwimmingMoveControl {
        private final TwinTailEntity axolotl;

        public TwinTailEntityMoveControl(TwinTailEntity pAxolotl) {
            super(pAxolotl, 85, 10, 0.1F, 0.5F, false);
            this.axolotl = pAxolotl;
        }

        public void tick() {
            if (this.axolotl.isAlive()) {
                super.tick();
            }

        }
    }

    static class TwinTailEntityPathNavigation extends WaterBoundPathNavigation {
        TwinTailEntityPathNavigation(TwinTailEntity pTwintail, Level pLevel) {
            super(pTwintail, pLevel);
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

    class TwinTailEntityLookControl extends SmoothSwimmingLookControl {
        public TwinTailEntityLookControl(TwinTailEntity pPupfish, int pMaxYRotFromCenter) {
            super(pPupfish, pMaxYRotFromCenter);
        }

        /**
         * Updates look
         */
        public void tick() {
            if (TwinTailEntity.this.isAlive()) {
                super.tick();
            }

        }
    }
}
