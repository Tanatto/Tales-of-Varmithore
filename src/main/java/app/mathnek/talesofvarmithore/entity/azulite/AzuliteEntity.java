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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AzuliteEntity extends Monster implements IAnimatable, Enemy {
    protected static final EntityDataAccessor<Integer> VARIANTS = SynchedEntityData.defineId(AzuliteEntity.class, EntityDataSerializers.INT);

    private AnimationFactory factory = new AnimationFactory(this);

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


    private static final AnimationBuilder HOVER = new AnimationBuilder().addAnimation("Hover");
    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("Idle");
    private static final AnimationBuilder FLAP = new AnimationBuilder().addAnimation("Flap");
    private static final AnimationBuilder BITE = new AnimationBuilder().addAnimation("Bite");

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(FLAP);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(HOVER);
        return PlayState.CONTINUE;
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
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
    public AnimationFactory getFactory() {
        return factory;
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
