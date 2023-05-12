package app.mathnek.talesofvarmithore.data.model;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.block.ToVBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlocksStateProvider extends BlockStateProvider {
    public BlocksStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TalesofVarmithore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ToVBlocks.LAVACORE_ORE.get());
        simpleBlock(ToVBlocks.MIRCH_LEAVES.get());
        simpleBlock(ToVBlocks.MIRCH_PLANKS.get());
        simpleBlock(ToVBlocks.MAGMA_BRICK.get());
        simpleBlock(ToVBlocks.HARD_GLASS.get());

        logBlock(ToVBlocks.MIRCH_LOG.get());
        simpleBlock(ToVBlocks.GRASS_BLOCK.get(), models().getExistingFile(
                new ResourceLocation(TalesofVarmithore.MOD_ID, "block/grass_block")));
    }

}