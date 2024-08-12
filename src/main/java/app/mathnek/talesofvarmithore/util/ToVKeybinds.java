package app.mathnek.talesofvarmithore.util;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = TalesofVarmithore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToVKeybinds {
    public static KeyMapping BITE;

    public static void init() {
        // Initialize the keybinding
        BITE = new KeyMapping("key." + TalesofVarmithore.MOD_ID + ".bite", InputConstants.Type.KEYSYM, InputConstants.KEY_R, "key.category." + TalesofVarmithore.MOD_ID);

        // Register to the mod event bus
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ToVKeybinds::registerKeyMappings);
    }

    // Register the KeyMapping during the RegisterKeyMappingsEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(BITE);
    }
}
