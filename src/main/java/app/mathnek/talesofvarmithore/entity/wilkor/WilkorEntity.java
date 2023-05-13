package app.mathnek.talesofvarmithore.entity.wilkor;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.ai.ToVHurtByTargetGoal;
import app.mathnek.talesofvarmithore.entity.ai.ToVMeleeAttackGoal;
import app.mathnek.talesofvarmithore.entity.ai.WilkorResetUniversalAngerTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class WilkorEntity extends BaseEntityClass implements IAnimatable {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME =
            SynchedEntityData.defineId(WilkorEntity.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public WilkorEntity(EntityType<? extends BaseEntityClass> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 6;
    }

    private int getTypeForBiome(ServerLevelAccessor pLevel) {
        Holder<Biome> biome = pLevel.getBiome(new BlockPos(this.position()));
        if (biome.is(BiomeTags.HAS_RUINED_PORTAL_DESERT) || biome.is(BiomeTags.HAS_DESERT_PYRAMID)) {
            return 4;
        }
        if (biome.is(BiomeTags.HAS_RUINED_PORTAL_DESERT) || biome.is(BiomeTags.HAS_DESERT_PYRAMID)) {
            return 5;
        }

        return 0;
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        } else {
            this.setVariant(getTypeForBiome(pLevel));
        }
        return pSpawnData;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));

        this.targetSelector.addGoal(2, (new OwnerHurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, (new OwnerHurtTargetGoal(this)));
        this.targetSelector.addGoal(8, new WilkorResetUniversalAngerTargetGoal<>(this, true));
        this.targetSelector.addGoal(8, new ToVMeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(8, new ToVHurtByTargetGoal(this).setAlertOthers());

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Sheep.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Ocelot.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cow.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Pig.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBear.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Panda.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SkeletonHorse.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Stray.class, true));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return ToVEntityTypes.WILKOR.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.MUTTON;
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
        event.getController().setAnimation(new AnimationBuilder().addAnimation("direwolf.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
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

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6D);
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.4f);
        } else {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(45.0D);
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5D);
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.3f);
        }
    }

    @Override
    public boolean isNocturnal() {
        return true;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_GROWL;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
}
