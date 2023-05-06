package app.mathnek.talesofvarmithore.entity.eggs;

import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.item.DragonEggItem;
import app.mathnek.talesofvarmithore.item.ToVItems;
import app.mathnek.talesofvarmithore.util.Util;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DragonEggBase extends AgeableMob implements IAnimatable {
   private static final EntityDataAccessor VARIANT = SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor TICK_HATCH_TIME = SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor CAN_HATCH = SynchedEntityData.defineId(RockDrakeEntity.class, EntityDataSerializers.BOOLEAN);
   protected RockDrakeEntity mobResult;
   private AnimationFactory factory = new AnimationFactory(this);
   protected boolean canHatch;
   public int displayProgressTicks = 0;

   public DragonEggBase(EntityType pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.setTicksToHatch(0);
   }

   public DragonEggBase(EntityType pEntityType, Level pLevel, boolean canHatch) {
      this(pEntityType, pLevel);
      this.setCanHatch(canHatch);
      this.setTicksToHatch(0);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.FOLLOW_RANGE, 0.5);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
      pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
      if (pReason == MobSpawnType.STRUCTURE) {
         this.setCanHatch(false);
      } else {
         this.setCanHatch(true);
      }

      return pSpawnData;
   }

   public boolean canBreed() {
      return false;
   }

   public void moveTo(Vec3 pVec) {
      super.moveTo(pVec);
   }

   public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
      return null;
   }

   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(VARIANT, 0);
      this.entityData.define(TICK_HATCH_TIME, 0);
      this.entityData.define(CAN_HATCH, true);
   }

   public void addAdditionalSaveData(CompoundTag pCompound) {
      super.addAdditionalSaveData(pCompound);
      pCompound.putInt("dragonVariant", this.getVariant());
      pCompound.putInt("daysToHatch", this.getTicksToHatch());
      pCompound.putBoolean("canHatch", this.canHatch());
   }

   public void readAdditionalSaveData(CompoundTag pCompound) {
      super.readAdditionalSaveData(pCompound);
      this.setVariant(pCompound.getInt("dragonVariant"));
      this.setTicksToHatch(pCompound.getInt("daysToHatch"));
      this.setCanHatch(pCompound.getBoolean("canHatch"));
   }

   public boolean isInvulnerableTo(@NotNull DamageSource pSource) {
      return pSource != DamageSource.IN_FIRE && pSource != DamageSource.ON_FIRE && pSource != DamageSource.FALL && pSource != DamageSource.LAVA && pSource != DamageSource.IN_WALL && pSource != DamageSource.CRAMMING && pSource != DamageSource.FLY_INTO_WALL && pSource != DamageSource.CACTUS && pSource != DamageSource.explosion((LivingEntity)null) ? super.isInvulnerableTo(pSource) : true;
   }

   public Iterable getArmorSlots() {
      return ImmutableList.of();
   }

   public ItemStack getItemBySlot(EquipmentSlot pSlot) {
      return ItemStack.EMPTY;
   }

   public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
   }

   public HumanoidArm getMainArm() {
      return HumanoidArm.RIGHT;
   }

   public Packet getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int dragonVariant) {
      this.entityData.set(VARIANT, dragonVariant);
   }

   public int getTicksToHatch() {
      return (Integer)this.entityData.get(TICK_HATCH_TIME);
   }

   public void setTicksToHatch(int hatch_time) {
      this.entityData.set(TICK_HATCH_TIME, hatch_time);
   }

   public boolean canHatch() {
      return (Boolean)this.entityData.get(CAN_HATCH);
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

   public void registerControllers(AnimationData data) {}

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public @NotNull InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
      ItemStack stack = pPlayer.getMainHandItem();
      if (stack.isEmpty()) {
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
      return ret.consumesAction() ? ret : InteractionResult.SUCCESS;
   }

   protected int getHatchTimeMinecraftDays() {
      return Util.mcDaysToMinutes(2);
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   public boolean isCold() {
      BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
      int i5 = this.level.getLightEngine().getLayerListener(LightLayer.BLOCK).getLightValue(pos);
      return i5 < 10;
   }

   protected boolean hatchTickParamters() {
      return !this.isCold();
   }

   protected boolean hatchParameters() {
      return this.getTicksToHatch() >= getHatchTime();
   }

   @Override
   public void tick() {
      super.tick();

      if(this.displayProgressTicks > 0)
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
      RockDrakeEntity dragonResult = this.getDragonEggResult();

      assert dragonResult != null;

      dragonResult.setAge(-100000);
      dragonResult.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
      //dragonResult.setFoodTameLimiterBar(40);
      dragonResult.setVariant(this.random.nextInt(dragonResult.getMaxAmountOfVariants()));
      this.level.addFreshEntity(dragonResult);
      this.discard();
      if (this.level instanceof ServerLevel) {
         ((ServerLevel)this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockParticle().defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), this.particleEggShellCount(), (double)(this.getBbWidth() / 3.0F), (double)(this.getBbHeight() / 3.0F), (double)(this.getBbWidth() / 3.0F), 0.1);
      }

      if (this.level.isClientSide) {
         this.level.playLocalSound(this.position().x(), this.position().y(), this.position().z(), SoundEvents.TURTLE_EGG_HATCH, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
      }

   }

   protected int particleEggShellCount() {
      return 55;
   }

   public boolean hurt(DamageSource pSource, float pAmount) {
      if (!this.isRemoved() && !this.level.isClientSide()) {
         DragonEggItem item = this.getItemVersion();
         this.spawnAtLocation(item);
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   public boolean fireImmune() {
      return true;
   }

   public boolean isOnFire() {
      return false;
   }

   protected DragonEggItem getItemVersion() {
      return (DragonEggItem) ToVItems.ROCKDRAKE_EGG.get();
   }

   protected RockDrakeEntity getDragonEggResult() {
      return (RockDrakeEntity)((EntityType) ToVEntityTypes.ROCKDRAKE_EGG.get()).create(this.level);
   }

   public Block getBlockParticle() {
      return Blocks.OBSIDIAN;
   }

   public float scale() {
      return 1.1F;
   }

   public int getHatchProgress() {
      return (int) Math.floor(((float) this.getTicksToHatch() / (float) this.getHatchTime()) * 100F);
   }

   protected int getHatchTime() {
      return 100;
   }
}
