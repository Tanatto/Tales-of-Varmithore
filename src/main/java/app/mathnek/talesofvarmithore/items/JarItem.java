package app.mathnek.talesofvarmithore.items;

import app.mathnek.talesofvarmithore.entity.moth_fae_dragon.MothFaeDragon;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;



public class JarItem extends Item {

  public JarItem(Properties properties) {
    super(properties);
  }

  @Override
  public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, Player playerIn, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
      ItemStack trueStack = playerIn.getItemInHand(hand);
      if (!playerIn.level.isClientSide && hand == InteractionHand.MAIN_HAND && target instanceof MothFaeDragon && (trueStack.getTag() == null || (trueStack.getTag() != null && trueStack.getTag().getCompound("EntityTag").isEmpty()))) {
          CompoundTag newTag = new CompoundTag();

          CompoundTag entityTag = new CompoundTag();
          target.save(entityTag);
          newTag.put("EntityTag", entityTag);

          newTag.putString("JarItemID", Registry.ENTITY_TYPE.getKey(target.getType()).toString());
          trueStack.setTag(newTag);

          playerIn.swing(hand);
          playerIn.level.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.NEUTRAL, 3.0F, 0.75F);
          target.remove(Entity.RemovalReason.DISCARDED);
          return InteractionResult.SUCCESS;
      }

      return InteractionResult.FAIL;
  }

  @Override
  public @NotNull InteractionResult useOn(UseOnContext context) {
    if (context.getClickedFace() != Direction.UP)
      return InteractionResult.FAIL;
    ItemStack stack = context.getItemInHand();
    if (stack.getTag() != null && !stack.getTag().getString("JarItemID").isEmpty()) {
      Level world = context.getLevel();
      String id = stack.getTag().getString("JarItemID");
      EntityType type = EntityType.byString(id).orElse(null);
      if (type != null) {
        Entity entity = type.create(world);
        if (entity instanceof MothFaeDragon dragon) {
          dragon.load(stack.getTag().getCompound("EntityTag"));
        }
        //Still needed to allow for intercompatibility
        if (stack.getTag().contains("EntityUUID"))
          entity.setUUID(stack.getTag().getUUID("EntityUUID"));
  
        entity.absMoveTo(context.getClickedPos().getX() + 0.5D, (context.getClickedPos().getY() + 1), context.getClickedPos().getZ() + 0.5D, 180 + (context.getHorizontalDirection()).toYRot(), 0.0F);
        if (world.addFreshEntity(entity)) {
          CompoundTag tag = stack.getTag();
          tag.remove("JarItemID");
          tag.remove("EntityTag");
          tag.remove("EntityUUID");
          stack.setTag(tag);
        }
      }
    }
    return InteractionResult.SUCCESS;
  }
}
