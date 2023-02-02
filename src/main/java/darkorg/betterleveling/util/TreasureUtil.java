package darkorg.betterleveling.util;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TreasureUtil {
    public static int getPotentialLoot(int pOriginalCount, double pPotentialLootBound, Random pRandom) {
        return Math.toIntExact(Math.round(pOriginalCount * MathHelper.nextDouble(pRandom, 0.0D, pPotentialLootBound)));
    }

    public static ItemStack getRandomTreasure(ITag.INamedTag<Item> pTag, Random pRandom) {
        return new ItemStack(pTag.getRandomElement(pRandom));
    }

    public static void spawnTreasure(ServerWorld pServerLevel, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(MathHelper.nextFloat(pRandom, 0.0F, 0.69F) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pRandom, pItemStack, pRandom.nextInt(30), true);
        } else {
            pItemStack.setCount(MathHelper.nextInt(pRandom, 1, 3));
        }
        Block.popResource(pServerLevel, pPos, pItemStack);
    }

}
