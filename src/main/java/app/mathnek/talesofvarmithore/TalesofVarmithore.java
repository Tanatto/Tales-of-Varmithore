package app.mathnek.talesofvarmithore;

import app.mathnek.talesofvarmithore.block.ToVBlocks;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteRenderer;
import app.mathnek.talesofvarmithore.entity.eggs.rockdrake.RockDrakeEggRenderer;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeRenderer;
import app.mathnek.talesofvarmithore.item.ToVItems;
import app.mathnek.talesofvarmithore.network.ControlNetwork;
import app.mathnek.talesofvarmithore.util.ToVKeybinds;
import app.mathnek.talesofvarmithore.sound.ToVSounds;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorRenderer;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TalesofVarmithore.MOD_ID)
public class TalesofVarmithore {
    public static final String MOD_ID = "tov";

    private static final Logger LOGGER = LogManager.getLogger();

    public TalesofVarmithore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ToVItems.register(eventBus);
        ToVBlocks.register(eventBus);
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
        EntityRenderers.register(ToVEntityTypes.ROCKDRAKE.get(), RockDrakeRenderer::new);

        EntityRenderers.register(ToVEntityTypes.ROCKDRAKE_EGG.get(), RockDrakeEggRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.PERSILA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.UNCIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ToVBlocks.HARD_GLASS.get(), RenderType.cutout());

        ToVKeybinds.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        ControlNetwork.init();
        event.enqueueWork(() -> {
        });

        SpawnPlacements.register(ToVEntityTypes.ROCKDRAKE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules);
    }
}
