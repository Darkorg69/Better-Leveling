package darkorg.betterleveling.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class StackUtil {
    public static boolean isCrop(ItemStack pItemStack) {
        return ItemUtil.isCrop(pItemStack.getItem());
    }

    public static boolean isSeed(ItemStack pItemStack) {
        return ItemUtil.isSeed(pItemStack.getItem());
    }

    public static boolean isSkin(ItemStack pItemStack) {
        return ItemUtil.isSkin(pItemStack.getItem());
    }

    public static boolean isMeat(ItemStack pItemStack) {
        return ItemUtil.isMeat(pItemStack.getItem());
    }

    public static boolean isBlacklistCrafting(ItemStack pItemStack) {
        return ItemUtil.isBlacklistCrafting(pItemStack.getItem());
    }

    public static boolean isSilktouch(ItemStack pItemStack) {
        return pItemStack != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, pItemStack) <= 0;
    }
}
