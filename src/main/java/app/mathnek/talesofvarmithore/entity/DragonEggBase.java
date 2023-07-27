package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.entity.twintail.TwinTailEntity;
import app.mathnek.talesofvarmithore.items.DragonEggItem;
import app.mathnek.talesofvarmithore.items.ToVItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DragonEggBase extends AgeableMob implements IAnimatable {
    private static final EntityDataAccessor<Integer> DRAGON_VARIANT = SynchedEntityData.defineId(DragonEggBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICK_HATCH_TIME = SynchedEntityData.defineId(DragonEggBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_HATCH = SynchedEntityData.defineId(DragonEggBase.class, EntityDataSerializers.BOOLEAN);
    protected TwinTailEntity dragonResult;
    public int displayProgressTicks = 0;

    AnimationFactory factory = new AnimationFactory(this);
    protected boolean canHatch;

    public DragonEggBase(EntityType<? extends DragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTicksToHatch(0);
    }

    public DragonEggBase(EntityType<? extends DragonEggBase> pEntityType, Level pLevel, boolean canHatch) {
        this(pEntityType, pLevel);
        this.setCanHatch(canHatch);
        this.setTicksToHatch(0);
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.FOLLOW_RANGE, 0.5D);

    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.STRUCTURE) {
            // do not make eggs spawning natural hatchable
            this.setCanHatch(false);
        } else {
            this.setCanHatch(true);
        }

        return pSpawnData;
    }


    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public void moveTo(Vec3 pVec) {
        super.moveTo(pVec);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DRAGON_VARIANT, 0);
        this.entityData.define(TICK_HATCH_TIME, 0);
        this.entityData.define(CAN_HATCH, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("dragonVariant", getDragonVariant());
        pCompound.putInt("daysToHatch", getTicksToHatch());
        pCompound.putBoolean("canHatch", canHatch());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setDragonVariant(pCompound.getInt("dragonVariant"));
        this.setTicksToHatch(pCompound.getInt("daysToHatch"));
        this.setCanHatch(pCompound.getBoolean("canHatch"));
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource pSource) {
        if (pSource == DamageSource.IN_FIRE || pSource == DamageSource.ON_FIRE || pSource == DamageSource.FALL || pSource == DamageSource.LAVA
                || pSource == DamageSource.IN_WALL || pSource == DamageSource.CRAMMING || pSource == DamageSource.FLY_INTO_WALL
                || pSource == DamageSource.CACTUS || pSource == DamageSource.explosion((LivingEntity) null)) {
            return true;
        } else {
            return super.isInvulnerableTo(pSource);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public int getDragonVariant() {
        return this.entityData.get(DRAGON_VARIANT);
    }

    public void setDragonVariant(int dragonVariant) {
        this.entityData.set(DRAGON_VARIANT, dragonVariant);
    }

    public int getTicksToHatch() {
        return this.entityData.get(TICK_HATCH_TIME);
    }

    public void setTicksToHatch(int hatch_time) {
        this.entityData.set(TICK_HATCH_TIME, hatch_time);
    }

    public boolean canHatch() {
        return this.entityData.get(CAN_HATCH);
    }

    public void setCanHatch(boolean canHatch) {
        this.entityData.set(CAN_HATCH, canHatch);
    }

    public ResourceLocation getTextureLocation(DragonEggBase dragonBase) {
        return null;
    }

    public ResourceLocation getModelLocation(DragonEggBase dragonBase) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public @NotNull InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        ItemStack stack = pPlayer.getMainHandItem();
        if (pPlayer.isCreative()) {
            if (stack.is(Items.STICK)) {
                hatch();
            }
        } else {
            String s = "egg.warm.toHatch";
            String s1 = "egg.cold.toHatch";
            if (!isCold()) {
                pPlayer.displayClientMessage(new TranslatableComponent(s), true);
                // We only set this on the client as it's used inside renderer, and we don't need the server
                if (this.level.isClientSide())
                    this.displayProgressTicks = 100; // 100 ticks is 5 seconds
            } else {
                pPlayer.displayClientMessage(new TranslatableComponent(s1), true);
            }
        }
        InteractionResult ret = super.interact(pPlayer, pHand);
        if (ret.consumesAction()) return ret;
        return InteractionResult.SUCCESS;
    }

    protected int getHatchTime() {
        return 100;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    public boolean isCold() {
        BlockPos pos = new BlockPos(getX(), getY(), getZ());
        int i5 = level.getLightEngine().getLayerListener(LightLayer.BLOCK).getLightValue(pos);
//        System.out.println("getLightEngine.getLayerListener(LightLayer.BLOCK).getLightValue() " + i5);
        return i5 < 10;
    }

    protected boolean hatchParameters() {
        return this.getTicksToHatch() >= getHatchTime();
    }


    @Override
    public void tick() {
        super.tick();

        if (this.displayProgressTicks > 0)
            this.displayProgressTicks--;

        if (!level.isClientSide() && this.tickCount % 20 == 0) {
            if (!isCold() && this.getTicksToHatch() <= getHatchTime()) {
                this.setTicksToHatch(getTicksToHatch() + 1);
            } else if (isCold() && getTicksToHatch() > 0) {
                this.setTicksToHatch(getTicksToHatch() - 1);
            }
        }

        if (hatchParameters()) {
            hatch();
        }
    }

    protected void hatch() {
        TwinTailEntity dragonResult = getDragonEggResult();
        assert dragonResult != null;
        dragonResult.setAge(-100000);
        dragonResult.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
        dragonResult.setVariant(random.nextInt(dragonResult.getMaxAmountOfVariants()));
        dragonResult.setTame(true);
        this.level.addFreshEntity(dragonResult);
        this.discard();
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, getBlockParticle().defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), particleEggShellCount(), (double) (this.getBbWidth() / 3.0F), (double) (this.getBbHeight() / 3.0F), (double) (this.getBbWidth() / 3.0F), 0.10D);
        }
        if (level.isClientSide)
            level.playLocalSound(position().x(), position().y(), position().z(), SoundEvents.TURTLE_EGG_HATCH, SoundSource.NEUTRAL, 1, 1, false);
    }

    protected int particleEggShellCount() {
        return 55;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!isRemoved()) {
            if (!level.isClientSide()) {
                DragonEggItem item = getItemVersion();
                this.spawnAtLocation(item);
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    protected DragonEggItem getItemVersion() {
        return ToVItems.TWINTAIL_EGG.get();
    }


    // stinger would be the default
    protected TwinTailEntity getDragonEggResult() {
        return ToVEntityTypes.TWINTAIL.get().create(this.level);
    }

    /**
     * call particle color from block form, since we lack the ability to set the color via RGB values
     */
    public Block getBlockParticle() {
        return Blocks.OBSIDIAN;
    }

    public float scale() {
        return 1.0F;
    }

    public int getHatchProgress() {
        return (int) Math.floor(((float) this.getTicksToHatch() / (float) this.getHatchTime()) * 100F);
    }
}