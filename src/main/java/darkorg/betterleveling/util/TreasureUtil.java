package darkorg.betterleveling.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class TreasureUtil {
    public static void spawnTreasure(ServerLevel pServerLevel, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(pRandom.nextFloat(0.0F, 0.69F) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pRandom, pItemStack, pRandom.nextInt(0, 30), true);
        } else {
            pItemStack.setCount(pRandom.nextInt(1, 3));
        }
        Block.popResource(pServerLevel, pPos, pItemStack);
    }

    public static ItemStack getRandom(TagKey<Item> pTag, Random pRandom) {
        return new ItemStack(Registry.ITEM.getTag(pTag).flatMap(tag -> tag.getRandomElement(pRandom)).orElseThrow());
    }
}
