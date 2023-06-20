package app.mathnek.talesofvarmithore.gui;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ToVContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<MenuType<EntityContainerMenu>> ENTITY_INV = CONTAINER_TYPES.register("entity_inv",
            () -> IForgeMenuType.create((windowId, inv, data) -> new EntityContainerMenu(windowId, inv, data.readInt())));

    public static void register(IEventBus eventBus) {
        CONTAINER_TYPES.register(eventBus);
    }
}
