package app.mathnek.talesofvarmithore.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ToVCreativeModeTab {
    public static final CreativeModeTab TOV_TAB = new CreativeModeTab("tov_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ToVItems.WILKOR_SPAWN_EGG.get());
        }
    };
}
