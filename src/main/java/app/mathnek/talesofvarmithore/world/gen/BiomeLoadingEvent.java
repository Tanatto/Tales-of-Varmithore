package app.mathnek.talesofvarmithore.world.gen;

import net.minecraftforge.eventbus.api.SubscribeEvent;


public class BiomeLoadingEvent {

    @SubscribeEvent
    public static void biomeLoadingEvent(final net.minecraftforge.event.world.BiomeLoadingEvent event) {
        ModEntityGeneration.onEntitySpawn(event);
        ToVFlowerGen.generateFlowers(event);
    }
}
