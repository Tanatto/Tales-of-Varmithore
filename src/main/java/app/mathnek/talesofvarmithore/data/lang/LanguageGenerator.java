package app.mathnek.talesofvarmithore.data.lang;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.item.ToVItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

	public LanguageGenerator(DataGenerator output) {
		super(output, TalesofVarmithore.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addBlocks();
		addItems();
		addMisc();
	}

	private void addBlocks() {

	}

	private void addItems() {
		addItem(ToVItems.WILKOR_SPAWN_EGG,"Wilkor");
		addItem(ToVItems.AZULITE_SPAWN_EGG,"Azulite");
		addItem(ToVItems.OBSAIDON_SHARD,"Obsaidon Shard");
		addItem(ToVItems.OBSAIDON_CRYSTAL,"Obsaidon Crystal");
		addItem(ToVItems.LAVACORE_INGOT,"Lavacore Ingot");
		addItem(ToVItems.LAVACORE_PICKAXE,"Lavacore Pickaxe");
		addItem(ToVItems.LAVACORE_AXE,"Lavacore Axe");
		addItem(ToVItems.LAVACORE_SWORD,"Lavacore Sword");
		addItem(ToVItems.LAVACORE_HOE,"Lavacore Hoe");
		addItem(ToVItems.LAVACORE_SHOVEL,"Lavacore Shovel");
		addItem(ToVItems.LAVACORE_HELMET,"Lavacore Helment");
		addItem(ToVItems.LAVACORE_CHESTPLATE,"Lavacore Chestplate");
		addItem(ToVItems.LAVACORE_LEGGINGS,"Lavacore Leggings");
		addItem(ToVItems.LAVACORE_BOOTS,"Lavacore Boots");
		addItem(ToVItems.OBSAIDON_HELMET,"Obsaidon Helment");
		addItem(ToVItems.OBSAIDON_CHESTPLATE,"Obsaidon Chestplate");
		addItem(ToVItems.OBSAIDON_LEGGINGS,"Obsaidon Leggings");
		addItem(ToVItems.OBSAIDON_BOOTS,"Obsaidon Boots");



	}

	private void addMisc() {
		add("itemGroup.tov_tab","Tales of Varmithore");
	}

}