package app.mathnek.talesofvarmithore.data.lang;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.block.ToVBlocks;
import app.mathnek.talesofvarmithore.item.ToVItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

	public LanguageGenerator(DataGenerator gen) {
		super(gen, TalesofVarmithore.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addBlocks();
		addItems();
		addMisc();
	}

	private void addBlocks() {
		addBlock(ToVBlocks.LAVACORE_ORE,"Lavacore Ore");
		addBlock(ToVBlocks.MIRCH_LEAVES,"Mirch Leaves");
		addBlock(ToVBlocks.MIRCH_LOG,"Mirch Log");
		addBlock(ToVBlocks.MIRCH_PLANKS,"Mirch Planks");
		addBlock(ToVBlocks.GRASS_BLOCK,"Grass Block");
		addBlock(ToVBlocks.HARD_GLASS,"Hard Glass");
		addBlock(ToVBlocks.MAGMA_BRICK,"Magma Brick");
		addBlock(ToVBlocks.PERSILA,"Persila Flower");
		addBlock(ToVBlocks.UNCIA,"Uncia Flower");

	}

	private void addItems() {
		addItem(ToVItems.WILKOR_SPAWN_EGG,"Wilkor");
		addItem(ToVItems.AZULITE_SPAWN_EGG,"Azulite");
		addItem(ToVItems.ROCKDRAKE_SPAWN_EGG,"RockDrake");
		addItem(ToVItems.ROCKDRAKE_EGG,"RockDrake Egg");
		addItem(ToVItems.PUPFISH_SPAWN_EGG,"Pupfish");
		addItem(ToVItems.OBSAIDON_SHARD,"Obsaidon Shard");
		addItem(ToVItems.FEATHER_TIPPED,"RockDrake Feather Tipped");
		addItem(ToVItems.FEATHER_TIPPED_BUNCH,"RockDrake Feather Tipped Bunched");
		addItem(ToVItems.ROCKDRAKE_MEAT,"RockDrake Meat");
		addItem(ToVItems.ROCKDRAKE_MEAT_COOKED,"RockDrake Meat Cooked");
		addItem(ToVItems.OBSAIDON_CRYSTAL,"Obsaidon Crystal");
		addItem(ToVItems.LAVACORE_INGOT,"Lavacore Ingot");
		addItem(ToVItems.LAVACORE_PICKAXE,"Lavacore Pickaxe");
		addItem(ToVItems.LAVACORE_AXE,"Lavacore Axe");
		addItem(ToVItems.LAVACORE_SWORD,"Lavacore Sword");
		addItem(ToVItems.LAVACORE_HOE,"Lavacore Hoe");
		addItem(ToVItems.LAVACORE_SHOVEL,"Lavacore Shovel");
		addItem(ToVItems.LAVACORE_HELMET,"Lavacore Helmet");
		addItem(ToVItems.LAVACORE_CHESTPLATE,"Lavacore Chestplate");
		addItem(ToVItems.LAVACORE_LEGGINGS,"Lavacore Leggings");
		addItem(ToVItems.LAVACORE_BOOTS,"Lavacore Boots");
		addItem(ToVItems.OBSAIDON_HELMET,"Obsaidon Helmet");
		addItem(ToVItems.OBSAIDON_CHESTPLATE,"Obsaidon Chestplate");
		addItem(ToVItems.OBSAIDON_LEGGINGS,"Obsaidon Leggings");
		addItem(ToVItems.OBSAIDON_BOOTS,"Obsaidon Boots");
		addItem(ToVItems.OBSAIDON_PICKAXE,"Obsaidon Pickaxe");
		addItem(ToVItems.OBSAIDON_AXE,"Obsaidon Axe");
		addItem(ToVItems.OBSAIDON_SWORD,"Obsaidon Sword");
		addItem(ToVItems.OBSAIDON_HOE,"Obsaidon Hoe");
		addItem(ToVItems.OBSAIDON_SHOVEL,"Obsaidon Shovel");



	}

	private void addMisc() {
		add("itemGroup.tov_tab","Tales of Varmithore");
		add("key.tov.bite","Bite");
		add("key.category.tov","Tales of Varmithore");
	}

}