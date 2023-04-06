package app.mathnek.talesofvarmithore.event;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TalesofVarmithore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToVEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ToVEntityTypes.WILKOR.get(), WilkorEntity.setAttributes());
    }
}
