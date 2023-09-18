package darkorg.betterleveling.api;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;

public interface IAbstractFurnaceBlockEntity {
    RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> bl$getQuickCheck();
}
