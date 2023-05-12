package app.mathnek.talesofvarmithore.data.loot;

import app.mathnek.talesofvarmithore.block.ToVBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {


    @Override
    protected void addTables() {
        this.dropSelf(ToVBlocks.LAVACORE_ORE.get());
        this.dropSelf(ToVBlocks.HARD_GLASS.get());
        this.dropSelf(ToVBlocks.GRASS_BLOCK.get());
        this.dropSelf(ToVBlocks.MAGMA_BRICK.get());
        this.dropSelf(ToVBlocks.UNCIA.get());
        this.dropSelf(ToVBlocks.PERSILA.get());
        this.dropSelf(ToVBlocks.MIRCH_LOG.get());
        this.dropSelf(ToVBlocks.MIRCH_PLANKS.get());
        this.dropSelf(ToVBlocks.MIRCH_LEAVES.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ToVBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}