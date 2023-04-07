package app.mathnek.talesofvarmithore.data;

import app.mathnek.talesofvarmithore.data.lang.LanguageGenerator;
import app.mathnek.talesofvarmithore.data.model.ItemModelGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class USCDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

		// Client data
		generator.addProvider(new LanguageGenerator(generator));
		generator.addProvider(new ItemModelGenerator(generator,event.getExistingFileHelper()));

		// Server data
	}
}