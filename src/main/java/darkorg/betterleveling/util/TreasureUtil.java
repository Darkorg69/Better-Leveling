package darkorg.betterleveling.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class TreasureUtil {
    public static int getPotentialLoot(int pOriginalCount, double pPotentialLootBound, Random pRandom) {
        return Math.toIntExact(Math.round(pOriginalCount * pRandom.nextDouble(pPotentialLootBound)));
    }

    public static void spawnTreasure(ServerLevel pServerLevel, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(pRandom.nextFloat(0.0F, 0.69F) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pServerLevel.random, pItemStack, pRandom.nextInt(0, 30), true);
        } else {
            pItemStack.setCount(pRandom.nextInt(1, 3));
        }
        Block.popResource(pServerLevel, pPos, pItemStack);
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getRandomTreasure(TagKey<Item> pTag, RandomSource pRandom) {
        return new ItemStack(Registry.ITEM.getTag(pTag).flatMap(pHolderSet -> pHolderSet.getRandomElement(pRandom)).orElseThrow());
    }
}
