package app.mathnek.talesofvarmithore.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class DragonSpawnEggItem extends ForgeSpawnEggItem {

    public DragonSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props ) {
        super(type, backgroundColor, highlightColor, props);
    }

    /*public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent mutablecomponent = new TranslatableComponent(s);
        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.BLUE};

        mutablecomponent.withStyle(achatformatting);
        pTooltipComponents.add(mutablecomponent);
    }*/
}
