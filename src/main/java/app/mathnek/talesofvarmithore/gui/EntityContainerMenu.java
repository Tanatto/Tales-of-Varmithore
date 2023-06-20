package app.mathnek.talesofvarmithore.gui;

import app.mathnek.talesofvarmithore.entity.EntitySaddleBase;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class EntityContainerMenu extends AbstractContainerMenu {
public EntitySaddleBase dragon;
   public Container dragonContainer;

   public EntityContainerMenu(int iD, Inventory playerInv, int entityID) {
      super(ToVContainers.ENTITY_INV.get(), iD);
      this.dragon = (EntitySaddleBase)playerInv.player.level.getEntity(entityID);
      this.dragonContainer = this.dragon.entityContainer;
      boolean i = true;
      this.dragonContainer.startOpen(playerInv.player);
      boolean j = true;
      // Saddle slot
      this.addSlot(new Slot(dragonContainer, 0, 8, 18) {
         @Override
         public boolean mayPlace(@NotNull ItemStack stack) {
            return stack.getItem() == Items.SADDLE && !this.hasItem();
         }

         @Override
         public boolean isActive() {
            return true;
         }
      });
      // Chest slot
      this.addSlot(new Slot(dragonContainer, 1, 8, 36) {
         @Override
         public boolean mayPlace(@NotNull ItemStack stack) {
            return stack.getItem() == Blocks.CHEST.asItem() && !this.hasItem();
         }

         @Override
         public boolean isActive() {
            return true;
         }
      });
      int j1;
      int k1;
      if (this.dragon.hasChest()) {
         for(j1 = 0; j1 < 3; ++j1) {
            for(k1 = 0; k1 < this.dragon.getInventoryColumns(); ++k1) {
               this.addSlot(new Slot(this.dragonContainer, 2 + k1 + j1 * this.dragon.getInventoryColumns(), 80 + k1 * 18, 18 + j1 * 18));
            }
         }
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInv, k1 + j1 * 9 + 9, 8 + k1 * 18, 102 + j1 * 18 + -18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInv, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean stillValid(@NotNull Player pPlayer) {
      return !this.dragon.hasInventoryChanged(this.dragonContainer) && this.dragon.isAlive() && this.dragon.distanceTo(pPlayer) < 8.0F;
   }

   private boolean hasChest(EntitySaddleBase p_150578_) {
      return p_150578_ != null && p_150578_.hasChest();
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(pIndex);
      if (slot.hasItem() && dragon.hasChest()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         int i = this.dragonContainer.getContainerSize();
         if (pIndex < i) {
            if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
            if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).mayPlace(itemstack1)) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
            int j = i + 27;
            int k = j + 9;
            if (pIndex >= j && pIndex < k) {
               if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (pIndex < j) {
               if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
               return ItemStack.EMPTY;
            }

            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   /**
    * Called when the dragonContainer is closed.
    */
   public void removed(Player pPlayer) {
      super.removed(pPlayer);
      this.dragonContainer.stopOpen(pPlayer);
   }
}
