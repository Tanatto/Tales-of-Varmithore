package app.mathnek.talesofvarmithore.world.gen;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.world.gen.flower.ToVFlowerGen;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TalesofVarmithore.MOD_ID)
        public class ToVWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        //ToVOreGeneration.generateOres(event);

        //ModTreeGeneration.generateTrees(event);
        ToVFlowerGen.generateFlowers(event);
    }
}
