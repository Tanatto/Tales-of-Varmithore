package app.mathnek.talesofvarmithore;

import app.mathnek.talesofvarmithore.entity.azulite.AzuliteRenderer;
import app.mathnek.talesofvarmithore.item.ToVItems;
import app.mathnek.talesofvarmithore.sound.ToVSounds;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TalesofVarmithore.MOD_ID)
public class TalesofVarmithore {
    public static final String MOD_ID = "tov";

    private static final Logger LOGGER = LogManager.getLogger();

    public TalesofVarmithore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ToVItems.register(eventBus);
        ToVSounds.register(eventBus);
        ToVEntityTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ToVEntityTypes.WILKOR.get(), WilkorRenderer::new);
        EntityRenderers.register(ToVEntityTypes.AZULITE.get(), AzuliteRenderer::new);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }
}
