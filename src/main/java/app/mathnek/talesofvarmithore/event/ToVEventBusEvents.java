package app.mathnek.talesofvarmithore.event;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TalesofVarmithore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToVEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ToVEntityTypes.WILKOR.get(), WilkorEntity.setAttributes());
        event.put(ToVEntityTypes.AZULITE.get(), AzuliteEntity.setAttributes());
        event.put(ToVEntityTypes.ROCKDRAKE.get(), RockDrakeEntity.setAttributes());

        //event.put(ToVEntityTypes.ROCKDRAKE_EGG.get(), RockDrakeEgg.createAttributes().build());
    }
}
