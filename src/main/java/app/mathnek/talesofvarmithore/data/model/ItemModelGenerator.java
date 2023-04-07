package app.mathnek.talesofvarmithore.data.model;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.item.ToVItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {

	public ItemModelGenerator(DataGenerator output, ExistingFileHelper existingFileHelper) {
		super(output, TalesofVarmithore.MOD_ID, existingFileHelper);
	}

	/**
	 * Generate a block item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void spawnEgg(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("minecraft:item/template_spawn_egg"));
	}


	@Override
	protected void registerModels() {
		spawnEgg(ToVItems.AZULITE_SPAWN_EGG.get());
		spawnEgg(ToVItems.WILKOR_SPAWN_EGG.get());

	}
}