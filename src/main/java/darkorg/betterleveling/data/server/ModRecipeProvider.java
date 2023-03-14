package darkorg.betterleveling.data.server;

import darkorg.betterleveling.registry.ModBlocks;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> pFinishedRecipeConsumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.RAW_IRON.get(), 9).requires(ModBlocks.RAW_IRON_BLOCK.get()).unlockedBy("has_raw_iron_block", has(ModBlocks.RAW_IRON_BLOCK.get())).save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(ModItems.RAW_GOLD.get(), 9).requires(ModBlocks.RAW_GOLD_BLOCK.get()).unlockedBy("has_raw_gold_block", has(ModBlocks.RAW_GOLD_BLOCK.get())).save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(ModItems.RAW_DEBRIS.get(), 9).requires(ModBlocks.RAW_DEBRIS_BLOCK.get()).unlockedBy("has_raw_debris_block", has(ModBlocks.RAW_DEBRIS_BLOCK.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModBlocks.RAW_IRON_BLOCK.get()).define('#', ModItems.RAW_IRON.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_raw_iron", has(ModItems.RAW_IRON.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModBlocks.RAW_GOLD_BLOCK.get()).define('#', ModItems.RAW_GOLD.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_raw_gold", has(ModItems.RAW_GOLD.get())).save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModBlocks.RAW_DEBRIS_BLOCK.get()).define('#', ModItems.RAW_DEBRIS.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get())).save(pFinishedRecipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_IRON.get()), Items.IRON_INGOT, 0.7F, 200).unlockedBy("has_raw_iron", has(ModItems.RAW_IRON.get())).save(pFinishedRecipeConsumer, getSmeltingRecipeName(ModItems.RAW_IRON.get(), Items.IRON_INGOT));
        CookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_IRON.get()), Items.IRON_INGOT, 0.7F, 100).unlockedBy("has_raw_iron", has(ModItems.RAW_IRON.get())).save(pFinishedRecipeConsumer, getBlastingRecipeName(ModItems.RAW_IRON.get(), Items.IRON_INGOT));
        CookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_GOLD.get()), Items.GOLD_INGOT, 1.0F, 200).unlockedBy("has_raw_gold", has(ModItems.RAW_GOLD.get())).save(pFinishedRecipeConsumer, getSmeltingRecipeName(ModItems.RAW_GOLD.get(), Items.GOLD_INGOT));
        CookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_GOLD.get()), Items.GOLD_INGOT, 1.0F, 100).unlockedBy("has_raw_gold", has(ModItems.RAW_GOLD.get())).save(pFinishedRecipeConsumer, getBlastingRecipeName(ModItems.RAW_GOLD.get(), Items.GOLD_INGOT));
        CookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DEBRIS.get()), Items.NETHERITE_SCRAP, 2.0F, 200).unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get())).save(pFinishedRecipeConsumer, getSmeltingRecipeName(ModItems.RAW_DEBRIS.get(), Items.NETHERITE_SCRAP));
        CookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DEBRIS.get()), Items.NETHERITE_SCRAP, 2.0F, 100).unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get())).save(pFinishedRecipeConsumer, getBlastingRecipeName(ModItems.RAW_DEBRIS.get(), Items.NETHERITE_SCRAP));
    }

    private String getSmeltingRecipeName(Item pIngredient, Item pResult) {
        return pResult.toString() + "_from_smelting_" + pIngredient.toString();
    }

    private String getBlastingRecipeName(Item pIngredient, Item pResult) {
        return pResult.toString() + "_from_blasting_" + pIngredient.toString();
    }
}
