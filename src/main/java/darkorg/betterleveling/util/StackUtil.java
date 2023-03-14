package darkorg.betterleveling.util;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

public class StackUtil {
    public static boolean isCrop(ItemStack pItemStack) {
        return pItemStack.is(Tags.Items.CROPS);
    }

    public static boolean isSeed(ItemStack pItemStack) {
        return pItemStack.is(Tags.Items.SEEDS);
    }

    public static boolean isSkin(ItemStack pItemStack) {
        return pItemStack.is(ModTags.Items.SKIN);
    }

    public static boolean isMeat(ItemStack pItemStack) {
        FoodProperties foodProperties = pItemStack.getFoodProperties(null);

        if (foodProperties != null) {
            return foodProperties.isMeat();
        }

        return pItemStack.is(ModTags.Items.MEAT);
    }

    public static boolean isBlacklistCrafting(ItemStack pItemStack) {
        return ItemUtil.isBlacklistCrafting(pItemStack.getItem());
    }
}
