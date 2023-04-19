package app.mathnek.talesofvarmithore.data.model;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.block.ToVBlocks;
import app.mathnek.talesofvarmithore.item.ToVItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
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
	private ItemModelBuilder simpleItem(Item item) {
		return withExistingParent(item.getRegistryName().getPath(),
				new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(TalesofVarmithore.MOD_ID,"item/" + item.getRegistryName().getPath()));
	}

	private ItemModelBuilder handheldItem(Item item) {
		return withExistingParent(item.getRegistryName().getPath(),
				new ResourceLocation("item/handheld")).texture("layer0",
				new ResourceLocation(TalesofVarmithore.MOD_ID,"item/" + item.getRegistryName().getPath()));
	}

	private void blockItem(Block item) {
		getBuilder(item.asItem().toString())
				.parent(new ModelFile.UncheckedModelFile(TalesofVarmithore.MOD_ID + ":block/" + item.asItem()));
	}

	@Override
	protected void registerModels() {
		spawnEgg(ToVItems.AZULITE_SPAWN_EGG.get());
		spawnEgg(ToVItems.WILKOR_SPAWN_EGG.get());
		simpleItem(ToVItems.LAVACORE_INGOT.get());
		simpleItem(ToVItems.OBSAIDON_SHARD.get());
		simpleItem(ToVItems.OBSAIDON_CRYSTAL.get());
		simpleItem(ToVItems.OBSAIDON_HELMET.get());
		simpleItem(ToVItems.OBSAIDON_CHESTPLATE.get());
		simpleItem(ToVItems.OBSAIDON_LEGGINGS.get());
		simpleItem(ToVItems.OBSAIDON_BOOTS.get());
		simpleItem(ToVItems.LAVACORE_HELMET.get());
		simpleItem(ToVItems.LAVACORE_CHESTPLATE.get());
		simpleItem(ToVItems.LAVACORE_LEGGINGS.get());
		simpleItem(ToVItems.LAVACORE_BOOTS.get());
		handheldItem(ToVItems.LAVACORE_AXE.get());
		handheldItem(ToVItems.LAVACORE_PICKAXE.get());
		handheldItem(ToVItems.LAVACORE_HOE.get());
		handheldItem(ToVItems.LAVACORE_SHOVEL.get());
		handheldItem(ToVItems.LAVACORE_SWORD.get());
		blockItem(ToVBlocks.LAVACORE_ORE.get());
		blockItem(ToVBlocks.MIRCH_LEAVES.get());
		blockItem(ToVBlocks.MIRCH_LOG.get());
		blockItem(ToVBlocks.GRASS_BLOCK.get());

	}
}