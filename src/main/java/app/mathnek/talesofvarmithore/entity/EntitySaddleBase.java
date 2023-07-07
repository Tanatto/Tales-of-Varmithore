package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.gui.EntityContainerMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class EntitySaddleBase extends EntityGroundBase implements ContainerListener {

    private static final EntityDataAccessor<Boolean> ID_CHEST = SynchedEntityData.defineId(EntitySaddleBase.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ID_SADDLE = SynchedEntityData.defineId(EntitySaddleBase.class, EntityDataSerializers.BOOLEAN);

    private boolean hasChestVarChanged = false;
    public SimpleContainer entityContainer;
    private LazyOptional itemHandler = null;

    public EntitySaddleBase(EntityType<? extends EntityGroundBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.createInventory();
    }

    public void openGUI(Player playerEntity) {
        if (!this.level.isClientSide() && (!this.isVehicle() || playerEntity.getVehicle() != this && this.isTame())) {
            if (playerEntity instanceof ServerPlayer) {
                Component dragonName = this.getName();
                int id = this.getId();
                NetworkHooks.openGui((ServerPlayer) playerEntity, new MenuProvider() {

                    @Override
                    public @NotNull Component getDisplayName() {
                        return dragonName;
                    }

                    @Override
                    public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
                        return new EntityContainerMenu(i, playerInventory, id);
                    }
                }, buf -> buf.writeInt(id));
            }
        }
    }

    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        boolean ownedByPlayer = this.isOwnedBy(pPlayer);


        if (pPlayer.isCrouching() && ownedByPlayer && !guiLocked() && !isCommandItem(itemstack)) {
            this.openGUI(pPlayer);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean hasChest() {
        return this.entityData.get(ID_CHEST);
    }

    public void setChest(boolean chested) {
        this.hasChestVarChanged = true;
        this.entityData.set(ID_CHEST, chested);
    }

    public boolean hasSaddle() {
        return this.entityData.get(ID_SADDLE);
    }

    public void setSaddle(boolean saddle) {
        this.entityData.set(ID_SADDLE, saddle);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_CHEST, false);
        this.entityData.define(ID_SADDLE, false);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("ChestedDragon", this.hasChest());
        pCompound.putBoolean("Saddled", this.hasSaddle());
        if (!this.entityContainer.getItem(0).isEmpty()) {
            pCompound.put("SaddleItem", this.entityContainer.getItem(0).save(new CompoundTag()));
        }

        if (!this.entityContainer.getItem(1).isEmpty()) {
            pCompound.put("ChestItem", this.entityContainer.getItem(1).save(new CompoundTag()));
        }

        if (this.hasChest()) {
            ListTag listtag = new ListTag();
            for (int i = 2; i < this.entityContainer.getContainerSize(); ++i) {
                ItemStack itemstack = this.entityContainer.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte) i);
                    itemstack.save(compoundtag);
                    listtag.add(compoundtag);
                }
            }
            pCompound.put("Items", listtag);
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setChest(pCompound.getBoolean("ChestedDragon"));
        this.setSaddle(pCompound.getBoolean("Saddled"));
        ItemStack itemstack;
        if (pCompound.contains("SaddleItem", 10)) {
            itemstack = ItemStack.of(pCompound.getCompound("SaddleItem"));
            if (itemstack.is(Items.SADDLE)) {
                this.entityContainer.setItem(0, itemstack);
            }
        }
        if (pCompound.contains("ChestItem", 10)) {
            itemstack = ItemStack.of(pCompound.getCompound("ChestItem"));
            if (itemstack.is(Items.CHEST)) {
                this.entityContainer.setItem(1, itemstack);
            }
        }
        this.createInventory();
        if (this.hasChest()) {
            ListTag listtag = pCompound.getList("Items", 10);

            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                if (j >= 2 && j < this.entityContainer.getContainerSize()) {
                    this.entityContainer.setItem(j, ItemStack.of(compoundtag));
                }
            }
        }
        this.updateContainerEquipment();
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.hasChest()) {
            if (!this.level.isClientSide) {
                this.spawnAtLocation(Blocks.CHEST);
            }

            this.setChest(false);
        }

        if (this.entityContainer != null) {
            for (int i = 0; i < this.entityContainer.getContainerSize(); ++i) {
                ItemStack itemstack = this.entityContainer.getItem(i);
                if (!itemstack.isEmpty()) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.entityContainer;
        this.entityContainer = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener((ContainerListener) this);
            int i = Math.min(simplecontainer.getContainerSize(), this.entityContainer.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.entityContainer.setItem(j, itemstack.copy());
                }
            }
        }

        this.entityContainer.addListener((ContainerListener) this);
        this.updateContainerEquipment();
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.entityContainer));
    }

    private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess() {
            public @NotNull ItemStack get() {
                return EntitySaddleBase.this.entityContainer.getItem(p_149503_);
            }

            public boolean set(@NotNull ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                } else {
                    EntitySaddleBase.this.entityContainer.setItem(p_149503_, p_149528_);
                    EntitySaddleBase.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    @Override
    public @NotNull SlotAccess getSlot(int pSlot) {
        return pSlot == 499 ? new SlotAccess() {
            public @NotNull ItemStack get() {
                return EntitySaddleBase.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            public boolean set(@NotNull ItemStack p_149485_) {
                if (p_149485_.isEmpty()) {
                    if (EntitySaddleBase.this.hasChest()) {
                        EntitySaddleBase.this.setChest(false);
                        EntitySaddleBase.this.createInventory();
                    }

                    return true;
                } else if (p_149485_.is(Items.CHEST)) {
                    if (!EntitySaddleBase.this.hasChest()) {
                        EntitySaddleBase.this.setChest(true);
                        EntitySaddleBase.this.createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : this.getBasicSlot(pSlot);
    }

    public SlotAccess getBasicSlot(int pSlot) {
        int i = pSlot - 400;
        if (i >= 0 && i < 2 && i < this.entityContainer.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (pStack) -> {
                    return pStack.isEmpty() || pStack.is(Items.SADDLE);
                });
            }

            if (i == 1) {

                return this.createEquipmentSlotAccess(i, (pStack) -> {
                    return pStack.isEmpty() || pStack.is(Items.CHEST);
                });
            }
        }

        int j = pSlot - 500 + 2;
        return j >= 2 && j < this.entityContainer.getContainerSize() ? SlotAccess.forContainer(this.entityContainer, j) : super.getSlot(pSlot);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setSaddle(!this.entityContainer.getItem(0).isEmpty());
            this.setChest(!this.entityContainer.getItem(1).isEmpty());
        }
    }

    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    public void containerChanged(Container pInvBasic) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
        boolean flag_a = this.hasChest();
        if (this.tickCount > 20 && !flag_a && this.hasChest()) {
            this.playSound(SoundEvents.DONKEY_CHEST, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTame();
    }

    public boolean isChestable() {
        return true;
    }

    @Override
    public void equipSaddle(@Nullable SoundSource p_21748_) {
        this.entityContainer.setItem(0, new ItemStack(Items.SADDLE));

    }

    @Override
    public boolean isSaddled() {
        return hasSaddle();
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.itemHandler != null) {
            LazyOptional oldHandler = this.itemHandler;
            this.itemHandler = null;
            oldHandler.invalidate();
        }
    }

    public boolean hasInventoryChanged(Container container) {
        return this.entityContainer != container;
    }

    public int getInventoryColumns() {
        return 5;
    }

    protected int getInventorySize() {
        return 17;
    }

    public boolean guiLocked() {
        return false;
    }
}
