package darkorg.betterleveling.data.server;

import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DEBRIS.get()), Items.NETHERITE_SCRAP, 2.0F, 200).group("netherite_scrap").unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get())).save(pFinishedRecipeConsumer, getSmeltingRecipeName(Items.NETHERITE_SCRAP));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DEBRIS.get()), Items.NETHERITE_SCRAP, 2.0F, 100).group("netherite_scrap").unlockedBy("has_raw_debris", has(ModItems.RAW_DEBRIS.get())).save(pFinishedRecipeConsumer, getBlastingRecipeName(Items.NETHERITE_SCRAP));
    }
}
