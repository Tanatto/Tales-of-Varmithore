package app.mathnek.talesofvarmithore;

import app.mathnek.talesofvarmithore.blocks.ToVBlocks;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteRenderer;
import app.mathnek.talesofvarmithore.entity.moth_fae_dragon.MothFaeDragonRender;
import app.mathnek.talesofvarmithore.entity.pupfish.PupfishRenderer;
import app.mathnek.talesofvarmithore.entity.twintail.TwinTailRenderer;
import app.mathnek.talesofvarmithore.entity.twintail.egg.TwinTailEggRenderer;
import app.mathnek.talesofvarmithore.entity.wilkor.RenderWilkor;
import app.mathnek.talesofvarmithore.gui.EntityInventoryScreen;
import app.mathnek.talesofvarmithore.gui.ToVContainers;
import app.mathnek.talesofvarmithore.items.ToVItems;
import app.mathnek.talesofvarmithore.messages.ControlNetwork;
import app.mathnek.talesofvarmithore.sound.ToVSounds;
import app.mathnek.talesofvarmithore.util.ToVKeybinds;
import app.mathnek.talesofvarmithore.world.ToVStructures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod(TalesofVarmithore.MOD_ID)
public class TalesofVarmithore {
    public static final String MOD_ID = "tov";

    public static final Logger LOGGER = LogManager.getLogger();

    public TalesofVarmithore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ToVItems.register(eventBus);
        ToVBlocks.register(eventBus);
        ToVSounds.register(eventBus);
        ToVEntityTypes.register(eventBus);
        ToVStructures.register(eventBus);
        ToVContainers.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ToVEntityTypes.WILKOR.get(), RenderWilkor::new);
        EntityRenderers.register(ToVEntityTypes.AZULITE.get(), AzuliteRenderer::new);
        EntityRenderers.register(ToVEntityTypes.TWINTAIL.get(), TwinTailRenderer::new);
        EntityRenderers.register(ToVEntityTypes.PUPFISH.get(), PupfishRenderer::new);
        EntityRenderers.register(ToVEntityTypes.MOTH_FAE_DRAGON.get(), MothFaeDragonRender::new);

        EntityRenderers.register(ToVEntityTypes.TWINTAIL_EGG.get(), TwinTailEggRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.PERSILA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.UNCIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.HARD_GLASS.get(), RenderType.cutout());

        ToVKeybinds.init();

        event.enqueueWork(() -> {
            MenuScreens.register(ToVContainers.ENTITY_INV.get(), EntityInventoryScreen::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        ControlNetwork.init();
        event.enqueueWork(() -> {
        });

        SpawnPlacements.register(ToVEntityTypes.TWINTAIL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules);

        SpawnPlacements.register(ToVEntityTypes.PUPFISH.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules);
    }
}
