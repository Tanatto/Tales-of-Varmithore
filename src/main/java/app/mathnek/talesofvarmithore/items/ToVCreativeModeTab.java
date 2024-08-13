package app.mathnek.talesofvarmithore.items;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.blocks.ToVBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ToVCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TOV_TAB =
            REGISTRY.register("tov_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.literal("Tales of Varmithore Tab"))
                            .icon(() -> new ItemStack(ToVItems.TWINTAIL_SPAWN_EGG.get()))
                            .displayItems((parameters, tabData) -> {
                                // List of Items in the Creative tab
                                tabData.accept(ToVItems.WILKOR_SPAWN_EGG.get());
                                tabData.accept(ToVItems.AZULITE_SPAWN_EGG.get());
                                tabData.accept(ToVItems.FEATHER_TIPPED.get());
                                tabData.accept(ToVItems.OBSAIDON_LEGGINGS.get());
                                tabData.accept(ToVItems.JAR.get());
                                tabData.accept(ToVItems.PUPFISH_SPAWN_EGG.get());
                                tabData.accept(ToVItems.TWINTAIL_SPAWN_EGG.get());
                                tabData.accept(ToVItems.MOTH_FAE_DRAGON_SPAWN_EGG.get());
                                tabData.accept(ToVItems.TWINTAIL_EGG.get());
                                tabData.accept(ToVItems.OBSAIDON_SHARD.get());
                                tabData.accept(ToVItems.FEATHER_TIPPED_BUNCH.get());
                                tabData.accept(ToVItems.TWINTAIL_MEAT.get());
                                tabData.accept(ToVItems.TWINTAIL_MEAT_COOKED.get());
                                tabData.accept(ToVItems.OBSAIDON_CRYSTAL.get());
                                tabData.accept(ToVItems.LAVACORE_INGOT.get());
                                tabData.accept(ToVItems.OBSAIDON_AXE.get());
                                tabData.accept(ToVItems.OBSAIDON_SWORD.get());
                                tabData.accept(ToVItems.OBSAIDON_HOE.get());
                                tabData.accept(ToVItems.OBSAIDON_SHOVEL.get());
                                tabData.accept(ToVItems.OBSAIDON_PICKAXE.get());
                                tabData.accept(ToVItems.LAVACORE_AXE.get());
                                tabData.accept(ToVItems.LAVACORE_SWORD.get());
                                tabData.accept(ToVItems.LAVACORE_HOE.get());
                                tabData.accept(ToVItems.LAVACORE_SHOVEL.get());
                                tabData.accept(ToVItems.LAVACORE_PICKAXE.get());
                                tabData.accept(ToVItems.LAVACORE_HELMET.get());
                                tabData.accept(ToVItems.LAVACORE_CHESTPLATE.get());
                                tabData.accept(ToVItems.LAVACORE_LEGGINGS.get());
                                tabData.accept(ToVItems.LAVACORE_BOOTS.get());
                                tabData.accept(ToVItems.OBSAIDON_HELMET.get());
                                tabData.accept(ToVItems.OBSAIDON_CHESTPLATE.get());
                                tabData.accept(ToVItems.OBSAIDON_LEGGINGS.get());
                                tabData.accept(ToVItems.OBSAIDON_BOOTS.get());

                                tabData.accept(ToVBlocks.MIRCH_LEAVES.get());
                                tabData.accept(ToVBlocks.MIRCH_LOG.get());
                                tabData.accept(ToVBlocks.MIRCH_PLANKS.get());
                                tabData.accept(ToVBlocks.MIRCH_STAIRS.get());
                                tabData.accept(ToVBlocks.MIRCH_SLAB.get());
                                tabData.accept(ToVBlocks.MIRCH_FENCE.get());
                                tabData.accept(ToVBlocks.HARD_GLASS.get());
                                tabData.accept(ToVBlocks.MAGMA_BRICK.get());
                                tabData.accept(ToVBlocks.GRASS_BLOCK.get());
                                tabData.accept(ToVBlocks.PERSILA.get());
                                tabData.accept(ToVBlocks.UNCIA.get());
                            }).build());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
