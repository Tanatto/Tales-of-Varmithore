package app.mathnek.talesofvarmithore.items;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.items.equipment.ToVArmorMaterials;
import app.mathnek.talesofvarmithore.items.equipment.ToVTiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.objectweb.asm.commons.JSRInlinerAdapter;

public class ToVItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<Item> WILKOR_SPAWN_EGG = ITEMS.register("wilkor_spawn_egg",
            () -> new ForgeSpawnEggItem(ToVEntityTypes.WILKOR, 0x948e8d, 0x3b3635,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<Item> AZULITE_SPAWN_EGG = ITEMS.register("azulite_spawn_egg",
            () -> new ForgeSpawnEggItem(ToVEntityTypes.AZULITE, 0x948e8d, 0x3b3635,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<Item> PUPFISH_SPAWN_EGG = ITEMS.register("pupfish_spawn_egg",
            () -> new ForgeSpawnEggItem(ToVEntityTypes.PUPFISH, 0xff62e4, 0xdfdfdf,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<RESpawnEggItem> TWINTAIL_SPAWN_EGG = ITEMS.register("twintail_spawn_egg", () ->
            new RESpawnEggItem(ToVEntityTypes.TWINTAIL, 0xffffff, 0xffffff,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).stacksTo(64)));

    public static final RegistryObject<Item> MOTH_FAE_DRAGON_SPAWN_EGG = ITEMS.register("moth_fae_dragon_spawn_egg",
            () -> new ForgeSpawnEggItem(ToVEntityTypes.MOTH_FAE_DRAGON, 0x302a2a, 0xc22f2f,
                    new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<DragonEggItem> TWINTAIL_EGG = ITEMS.register("twintail_egg",
            () -> new DragonEggItem(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).stacksTo(4),
                    ToVEntityTypes.TWINTAIL_EGG));

    public static final RegistryObject<Item> OBSAIDON_SHARD = ITEMS.register("obsaidon_shard",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<Item> FEATHER_TIPPED = ITEMS.register("feather_tipped",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<Item> FEATHER_TIPPED_BUNCH = ITEMS.register("feather_tipped_bunch",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));

    public static final RegistryObject<Item> JAR = ITEMS.register("jar",
            () -> new JarItem(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).stacksTo(16)));

    public static final RegistryObject<Item> TWINTAIL_MEAT = ITEMS.register("twintail_meat",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).food(ToVFood.ROCKDRAKE_MEAT).stacksTo(64)));

    public static final RegistryObject<Item> TWINTAIL_MEAT_COOKED = ITEMS.register("twintail_meat_cooked",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).food(ToVFood.ROCKDRAKE_MEAT_COOKED).stacksTo(64)));

    public static final RegistryObject<Item> OBSAIDON_CRYSTAL = ITEMS.register("obsaidon_crystal",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB)));
    public static final RegistryObject<Item> LAVACORE_INGOT = ITEMS.register("lavacore_ingot",
            () -> new Item(new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> OBSAIDON_AXE = ITEMS.register("obsaidon_axe",
            () -> new AxeItem(ToVTiers.OBSAIDON, 5.0F, -3.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> OBSAIDON_SWORD = ITEMS.register("obsaidon_sword",
            () -> new SwordItem(ToVTiers.OBSAIDON, 3, -2.4F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> OBSAIDON_HOE = ITEMS.register("obsaidon_hoe",
            () -> new HoeItem(ToVTiers.OBSAIDON, -3, 0.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> OBSAIDON_SHOVEL = ITEMS.register("obsaidon_shovel",
            () -> new ShovelItem(ToVTiers.OBSAIDON, 1.5F, -3.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> OBSAIDON_PICKAXE = ITEMS.register("obsaidon_pickaxe",
            () -> new PickaxeItem(ToVTiers.OBSAIDON, 1, -2.8F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> LAVACORE_AXE = ITEMS.register("lavacore_axe",
            () -> new AxeItem(ToVTiers.LAVACORE, 5.0F, -3.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> LAVACORE_SWORD = ITEMS.register("lavacore_sword",
            () -> new SwordItem(ToVTiers.LAVACORE, 3, -2.4F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> LAVACORE_HOE = ITEMS.register("lavacore_hoe",
            () -> new HoeItem(ToVTiers.LAVACORE, -3, 0.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> LAVACORE_SHOVEL = ITEMS.register("lavacore_shovel",
            () -> new ShovelItem(ToVTiers.LAVACORE, 1.5F, -3.0F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));

    public static final RegistryObject<Item> LAVACORE_PICKAXE = ITEMS.register("lavacore_pickaxe",
            () -> new PickaxeItem(ToVTiers.LAVACORE, 1, -2.8F, (new Item.Properties()).tab(ToVCreativeModeTab.TOV_TAB).fireResistant()));
    public static final RegistryObject<Item> LAVACORE_HELMET = ITEMS.register("lavacore_helmet",
            () -> new ArmorItem(ToVArmorMaterials.LAVACORE, EquipmentSlot.HEAD, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).fireResistant())));
    public static final RegistryObject<Item> LAVACORE_CHESTPLATE = ITEMS.register("lavacore_chestplate",
            () -> new ArmorItem(ToVArmorMaterials.LAVACORE, EquipmentSlot.CHEST, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).fireResistant())));
    public static final RegistryObject<Item> LAVACORE_LEGGINGS = ITEMS.register("lavacore_leggings",
            () -> new ArmorItem(ToVArmorMaterials.LAVACORE, EquipmentSlot.LEGS, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).fireResistant())));
    public static final RegistryObject<Item> LAVACORE_BOOTS = ITEMS.register("lavacore_boots",
            () -> new ArmorItem(ToVArmorMaterials.LAVACORE, EquipmentSlot.FEET, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB).fireResistant())));
    public static final RegistryObject<Item> OBSAIDON_HELMET = ITEMS.register("obsaidon_helmet",
            () -> new ArmorItem(ToVArmorMaterials.OBSAIDON, EquipmentSlot.HEAD, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB))));
    public static final RegistryObject<Item> OBSAIDON_CHESTPLATE = ITEMS.register("obsaidon_chestplate",
            () -> new ArmorItem(ToVArmorMaterials.OBSAIDON, EquipmentSlot.CHEST, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB))));
    public static final RegistryObject<Item> OBSAIDON_LEGGINGS = ITEMS.register("obsaidon_leggings",
            () -> new ArmorItem(ToVArmorMaterials.OBSAIDON, EquipmentSlot.LEGS, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB))));
    public static final RegistryObject<Item> OBSAIDON_BOOTS = ITEMS.register("obsaidon_boots",
            () -> new ArmorItem(ToVArmorMaterials.OBSAIDON, EquipmentSlot.FEET, (new Item.Properties().tab(ToVCreativeModeTab.TOV_TAB))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
