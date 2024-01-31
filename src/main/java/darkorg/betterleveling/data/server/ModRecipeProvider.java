package darkorg.betterleveling.data.server;

import darkorg.betterleveling.registry.ModBlocks;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pPackOutput) {
        super(pPackOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_DEBRIS.get(), 9)
                .requires(ModBlocks.RAW_DEBRIS_BLOCK.get())
                .unlockedBy("has_raw_debris_block", has(ModBlocks.RAW_DEBRIS_BLOCK.get()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_DEBRIS_BLOCK.get())
                .define('#', ModItems.RAW_DEBRIS.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get()))
                .save(pFinishedRecipeConsumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DEBRIS.get()), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 2.0F, 200)
                .unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get()))
                .save(pFinishedRecipeConsumer, getSmeltingRecipeName(ModItems.RAW_DEBRIS.get(), Items.NETHERITE_SCRAP));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DEBRIS.get()), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 2.0F, 100)
                .unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get()))
                .save(pFinishedRecipeConsumer, getBlastingRecipeName(ModItems.RAW_DEBRIS.get(), Items.NETHERITE_SCRAP));
    }

    private String getSmeltingRecipeName(Item pIngredient, Item pResult) {
        return pResult.toString() + "_from_smelting_" + pIngredient.toString();
    }

    private String getBlastingRecipeName(Item pIngredient, Item pResult) {
        return pResult.toString() + "_from_blasting_" + pIngredient.toString();
    }
}
