package darkorg.betterleveling.util;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;

public class ItemUtil {
    public static boolean isCrop(Item pItem) {
        return pItem.is(Tags.Items.CROPS);
    }

    public static boolean isSeed(Item pItem) {
        return pItem.is(Tags.Items.SEEDS);
    }

    public static boolean isSkin(Item pItem) {
        return pItem.is(ModTags.Items.SKIN);
    }

    public static boolean isMeat(Item pItem) {
        Food foodProperties = pItem.getFoodProperties();

        if (foodProperties != null) {
            return foodProperties.isMeat();
        }

        return pItem.is(ModTags.Items.MEAT);
    }

    public static boolean isBlacklistCrafting(Item pItem) {
        return CraftingUtil.getCraftingBlacklist().contains(pItem);
    }
}
