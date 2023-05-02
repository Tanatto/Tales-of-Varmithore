package app.mathnek.talesofvarmithore.data.recipe;

import app.mathnek.talesofvarmithore.block.ToVBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(ToVBlocks.MAGMA_BRICK.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .define('B', Items.BLACK_DYE)
                .define('S', Blocks.STONE_BRICKS)
                .pattern(" M ")
                .pattern("BSB")
                .pattern(" M ")
                .unlockedBy("has_magma_blocks", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.MAGMA_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ToVBlocks.HARD_GLASS.get())
                .define('I', Items.IRON_INGOT)
                .define('G', Blocks.GLASS)
                .pattern(" I ")
                .pattern(" G ")
                .pattern(" I ")
                .unlockedBy("has_iron", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);
    }
}
