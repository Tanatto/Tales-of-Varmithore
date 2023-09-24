package app.mathnek.talesofvarmithore.items;

import app.mathnek.talesofvarmithore.entity.moth_fae_dragon.MothFaeDragon;
import app.mathnek.talesofvarmithore.items.JarItem;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class JarredFae extends Item {

  public JarredFae(Properties properties) {
    super(properties);
  }

  @Overide
  public @Notnull InteractionResult useOn(UseOnContext pContext) {
    BlockPos pos = pContext.getClickedPos();
    Level level = pContext.getLevel();
    MothFaeDragon LivingEntity = (MothFaeDragon) MothFaeVariants.get().create(level)
    playerHeldItem.shrink(1);
    p.getInventory().addItem(JarItem);
    return InteractionResult.SUCCESS;
  }
}
