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

	}

	private void addMisc() {
		add("itemGroup.tov_tab","Tales of Varmithore");
	}

}