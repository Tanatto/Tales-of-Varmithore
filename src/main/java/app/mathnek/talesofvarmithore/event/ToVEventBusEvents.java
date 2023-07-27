package app.mathnek.talesofvarmithore.event;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import app.mathnek.talesofvarmithore.entity.pupfish.PupfishEntity;
import app.mathnek.talesofvarmithore.entity.twintail.TwinTailEntity;
import app.mathnek.talesofvarmithore.entity.twintail.egg.TwinTailEgg;
import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TalesofVarmithore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToVEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ToVEntityTypes.WILKOR.get(), EntityWilkor.setAttributes().build());
        event.put(ToVEntityTypes.AZULITE.get(), AzuliteEntity.setAttributes());
        event.put(ToVEntityTypes.TWINTAIL.get(), TwinTailEntity.setAttributes().build());
        event.put(ToVEntityTypes.PUPFISH.get(), PupfishEntity.createAttributes().build());

        event.put(ToVEntityTypes.TWINTAIL_EGG.get(), TwinTailEgg.createAttributes().build());
    }
}
