package app.mathnek.talesofvarmithore.item;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ToVItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<Item> WILKOR_SPAWN_EGG = ITEMS.register("wilkor_spawn_egg",
            () -> new ForgeSpawnEggItem(ToVEntityTypes.WILKOR,0x948e8d, 0x3b3635,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
