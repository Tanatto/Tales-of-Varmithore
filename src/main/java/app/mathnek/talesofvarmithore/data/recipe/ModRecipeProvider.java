package app.mathnek.talesofvarmithore.data.recipe;

import app.mathnek.talesofvarmithore.block.ToVBlocks;
import app.mathnek.talesofvarmithore.item.ToVItems;
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

        ShapedRecipeBuilder.shaped(ToVItems.LAVACORE_AXE.get()).define('#', Items.STICK).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_lavacore", has(ToVItems.LAVACORE_INGOT.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.LAVACORE_HOE.get())).define('#', Items.STICK).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_lavacore", has(ToVItems.LAVACORE_INGOT.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.LAVACORE_PICKAXE.get())).define('#', Items.STICK).define('X',ToVItems.LAVACORE_INGOT.get()).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_lavacore", has(ToVItems.LAVACORE_INGOT.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.LAVACORE_SHOVEL.get())).define('#', Items.STICK).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("X").pattern("#").pattern("#").unlockedBy("has_lavacore", has(ToVItems.LAVACORE_INGOT.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.LAVACORE_SWORD.get())).define('#', Items.STICK).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_lavacore", has(ToVItems.LAVACORE_INGOT.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.LAVACORE_BOOTS.get()).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("X X").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.LAVACORE_CHESTPLATE.get()).define('X',ToVItems.LAVACORE_INGOT.get()).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.LAVACORE_HELMET.get()).define('X', ToVItems.LAVACORE_INGOT.get()).pattern("XXX").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.LAVACORE_LEGGINGS.get()).define('X',ToVItems.LAVACORE_INGOT.get()).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ToVItems.OBSAIDON_AXE.get()).define('#', Items.STICK).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.OBSAIDON_HOE.get())).define('#', Items.STICK).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.OBSAIDON_PICKAXE.get())).define('#', Items.STICK).define('X',ToVItems.OBSAIDON_CRYSTAL.get()).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.OBSAIDON_SHOVEL.get())).define('#', Items.STICK).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("X").pattern("#").pattern("#").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped((ToVItems.OBSAIDON_SWORD.get())).define('#', Items.STICK).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.OBSAIDON_BOOTS.get()).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("X X").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.OBSAIDON_CHESTPLATE.get()).define('X',ToVItems.OBSAIDON_CRYSTAL.get()).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.OBSAIDON_HELMET.get()).define('X', ToVItems.OBSAIDON_CRYSTAL.get()).pattern("XXX").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ToVItems.OBSAIDON_LEGGINGS.get()).define('X',ToVItems.OBSAIDON_CRYSTAL.get()).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_obsaidon_shard", has(ToVItems.OBSAIDON_CRYSTAL.get())).save(pFinishedRecipeConsumer);

    }


}
