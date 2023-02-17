package darkorg.betterleveling.util;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackUtil {
    public static boolean isResource(ItemStack pItemStack) {
        return pItemStack.is(ModTags.Items.RESOURCES);
    }

    public static boolean isCrop(ItemStack pItemStack) {
        return pItemStack.is(Tags.Items.CROPS);
    }

    public static boolean isSeed(ItemStack pItemStack) {
        return pItemStack.is(Tags.Items.SEEDS);
    }

    public static boolean isCropOrSeed(ItemStack pItemStack) {
        return StackUtil.isCrop(pItemStack) || StackUtil.isSeed(pItemStack);
    }

    public static boolean isSkin(ItemStack pItemStack) {
        return pItemStack.is(ModTags.Items.SKIN);
    }

    public static boolean isMeat(ItemStack pItemStack) {
        boolean isMeat = false;

        FoodProperties foodProperties = pItemStack.getFoodProperties(null);

        if (foodProperties != null) {
            isMeat = foodProperties.isMeat();
        }

        return isMeat || pItemStack.is(ModTags.Items.MEAT);
    }

    public static List<ItemStack> getIngredients(Container pContainer) {
        Map<Item, Integer> ingredientsMap = new HashMap<>();

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack stack = pContainer.getItem(i);
            if (!stack.isEmpty() && !stack.hasContainerItem()) {
                Item ingredient = stack.getItem();
                if (!ingredientsMap.containsKey(ingredient)) {
                    ingredientsMap.put(ingredient, 1);
                } else {
                    int count = ingredientsMap.get(ingredient);
                    ingredientsMap.put(ingredient, count + 1);
                }
            }
        }

        List<ItemStack> ingredients = new ArrayList<>();

        ingredientsMap.forEach((ingredient, count) -> {
            ingredients.add(new ItemStack(ingredient, count));
        });

        return ingredients;
    }
}
