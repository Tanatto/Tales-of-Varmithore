package app.mathnek.talesofvarmithore.block;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.item.ToVCreativeModeTab;
import app.mathnek.talesofvarmithore.item.ToVItems;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ToVBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<Block> LAVACORE_ORE = registerBlock("lavacore_ore",
            () ->new OreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<Block> MIRCH_LEAVES = registerBlock("mirch_leaves",
            () ->new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LEAVES)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<RotatedPillarBlock> MIRCH_LOG = registerBlock("mirch_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<RotatedPillarBlock> MIRCH_PLANKS = registerBlock("mirch_planks",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<Block> MAGMA_BRICK = registerBlock("magma_brick",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(8.0F, 3.0F)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<GlassBlock> HARD_GLASS = registerBlock("hard_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(1.0F, 50.0F)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<GrassBlock> GRASS_BLOCK = registerBlock("grass_block",
            () -> new GrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)),ToVCreativeModeTab.TOV_TAB);

    public static final RegistryObject<Block> PERSILA = registerBlock("persila",
            () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)),ToVCreativeModeTab.TOV_TAB);
    public static final RegistryObject<Block> UNCIA = registerBlock("uncia",
            () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)),ToVCreativeModeTab.TOV_TAB);
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ToVItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }




}
