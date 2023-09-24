package app.mathnek.talesofvarmithore.items;

import app.mathnek.talesofvarmithore.entity.moth_fae_dragon.MothFaeDragon;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class JarredFae extends Item {

  public JarredFae(Properties properties) {
    super(properties);
  }

  @Override
  public @NotNull InteractionResult useOn(UseOnContext pContext) {
    ItemStack playerHeldItem = pContext.getItemInHand();
    BlockPos pos = pContext.getClickedPos();
    Level level = pContext.getLevel();
    /*
    add in code that summons FaeMothDragon here
     */
    playerHeldItem.shrink(1);

    return InteractionResult.SUCCESS;
  }
}
