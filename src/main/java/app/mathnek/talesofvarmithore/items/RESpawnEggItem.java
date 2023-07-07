package app.mathnek.talesofvarmithore.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class RESpawnEggItem extends ForgeSpawnEggItem {
    //String s;

    public RESpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor,/* String info, */Properties props ) {
        super(type, backgroundColor, highlightColor, props);
        //s = info;
    }

    /*public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent mutablecomponent = new TranslatableComponent(s);
        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.BLUE};

        mutablecomponent.withStyle(achatformatting);
        pTooltipComponents.add(mutablecomponent);
    }*/
}
