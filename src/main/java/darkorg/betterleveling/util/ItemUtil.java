package darkorg.betterleveling.util;

import net.minecraft.world.item.Item;

public class ItemUtil {
    public static boolean isBlacklistCrafting(Item pItem) {
        return CraftingUtil.getCraftingBlacklist().contains(pItem);
    }
}
