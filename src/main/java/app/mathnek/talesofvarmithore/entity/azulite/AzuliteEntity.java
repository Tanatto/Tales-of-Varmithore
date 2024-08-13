package app.mathnek.talesofvarmithore.entity.azulite;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzuliteEntity extends Monster implements GeoEntity, Enemy {
    protected static final EntityDataAccessor<Integer> VARIANTS = SynchedEntityData.defineId(AzuliteEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AzuliteEntity(EntityType<? extends Monster> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.FLYING_SPEED, 0.38f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
    }

    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new FlyingPathNavigation(this, pLevel);
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (event.isMoving()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("flap"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("hover"));
    }


    //TODO: make custom biometag that include all biomes, Placeholder tags used
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        //TODO: Add back once I know which variant is which
        /*Holder<Biome> holder = pLevel.getBiome(this.blockPosition());
        if (holder.is(BiomeTags.HAS_IGLOO)) {
            this.setVariant(AzuliteVariants.SNOW);
        }
        if (holder.is(BiomeTags.HAS_DESERT_PYRAMID)) {
            this.setVariant(AzuliteVariants.SAND);
        }
        if (holder.is(BiomeTags.IS_HILL)) {
            this.setVariant(AzuliteVariants.HILLS);
        }
        if (holder.is(BiomeTags.IS_JUNGLE)) {
            this.setVariant(AzuliteVariants.JUNGLE);
        }
        if (holder.is(BiomeTags.IS_OCEAN)) {
            this.setVariant(AzuliteVariants.OCEAN);
        }
        if (holder.is(BiomeTags.IS_NETHER)) {
            this.setVariant(AzuliteVariants.NETHER);
        }
        if (holder.is(BiomeTags.HAS_END_CITY)) {
            this.setVariant(AzuliteVariants.END);
        }
        if (holder.is(BiomeTags.HAS_VILLAGE_SAVANNA)) {
            this.setVariant(AzuliteVariants.SAVANNA);
        }*/
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        }
        return pSpawnData;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 5, this::movementPredicate));
    }

    public int getVariant() {
        return this.entityData.get(VARIANTS);
    }

    public void setVariant(int pType) {
        this.entityData.set(VARIANTS, pType);
    }

    public int getMaxAmountOfVariants() {
        return 8;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("variant", this.getVariant());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANTS, 0);
    }
}
