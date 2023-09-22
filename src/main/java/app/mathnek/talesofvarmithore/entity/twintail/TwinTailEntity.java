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
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class TwinTailEntity extends EntitySaddleBase {

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
    }

    public static AttributeSupplier.Builder setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.5f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.JUMP_STRENGTH, 2);
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 14;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        /*if (this.isBaby()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.walk", true));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && this.isVehicle()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.run", true));
                return PlayState.CONTINUE;
            }
            if (this.isEntitySitting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.sit", true));
                return PlayState.CONTINUE;
            }
            if (this.isEntitySleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.sleep", true));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && this.isInWater()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.swim", true));
                return PlayState.CONTINUE;
            }

            event.getController().setAnimation(new AnimationBuilder().addAnimation("baby", true));
            return PlayState.CONTINUE;
        }*/
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.walk", true));
            return PlayState.CONTINUE;
        }
        if (this.isVehicle() && event.isMoving() && this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.run", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isVehicle() && isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.swim", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.swim", true));
            return PlayState.CONTINUE;
        }
        if (this.isEntitySitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.sit", true));
            return PlayState.CONTINUE;
        }
        if (this.isEntitySleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.sleep", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.swim", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.idle", true));
        return PlayState.CONTINUE;
    }


    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (IsBiting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drake.bite", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
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
        if (s.equals("Bluedude") || s.equals("bluedude")) {
            this.setVariant(15);
        }

        if (level.isClientSide()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageBite(IsBitingDamageTrue(), this.getId()));
        }

        if (level.isClientSide()) {
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

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddMobPacket mobPacket) {
        super.recreateFromPacket(mobPacket);
        PartEntity<?>[] stingerPart = this.getParts();

        for (int i = 0; i < stingerPart.length; ++i) {
            stingerPart[i].setId(i + mobPacket.getId());
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddMobPacket(this);
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

                if (!level.isClientSide()) {
                    this.hurt(this.level.getEntities(this, this.rockdrakeBiteOffset.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                }
            }
        }
    }

    private void hurt(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity) {
                entity.hurt(DamageSource.mobAttack(this), 8.0F);
                this.doEnchantDamageEffects(this, entity);
            }
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    private int getTypeForBiome(ServerLevelAccessor pLevel) {
        Holder<Biome> biome = pLevel.getBiome(new BlockPos(this.position()));
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
        data.addAnimationController(new AnimationController(this, "attack_Controller", 5, this::attackController));
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
}
