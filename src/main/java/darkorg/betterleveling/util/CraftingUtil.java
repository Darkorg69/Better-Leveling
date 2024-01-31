package darkorg.betterleveling.util;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingUtil {
    public static List<Item> getCraftingBlacklist() {
        List<Item> blacklist = new ArrayList<>();

        String config = ModConfig.SPECIALIZATIONS.craftingBlacklist.get();

        if (!config.isEmpty()) {
            if (config.matches("^(\\w+:\\w+,)*(\\w+:\\w+)$")) {
                for (String s : config.split(",")) {
                    String[] modName = s.split(":");
                    ResourceLocation location = new ResourceLocation(modName[0], modName[1]);
                    Item item = ForgeRegistries.ITEMS.getValue(location);
                    if (item != null) {
                        blacklist.add(item);
                    }
                }
            } else {
                BetterLeveling.LOGGER.warn("Malformed blacklist value! " + "Cannot parse string `" + config + "`");
            }
        }

        return blacklist;
    }

    public static List<ItemStack> getIngredients(Container pInventory) {
        Map<Item, Integer> ingredientsMap = new HashMap<>();

        for (int i = 0; i < pInventory.getContainerSize(); i++) {
            ItemStack stack = pInventory.getItem(i);
            if (!stack.isEmpty() && !stack.hasCraftingRemainingItem()) {
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

        ingredientsMap.forEach((ingredient, count) -> ingredients.add(new ItemStack(ingredient, count)));

        return ingredients;
    }
}
