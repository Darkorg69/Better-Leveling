package darkorg.betterleveling.util;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TreasureUtil {
    public static void spawnTreasure(ServerWorld pServerLevel, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(RandomValueRange.between(0.0F, 0.69F).getFloat(pRandom) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pRandom, pItemStack, RandomValueRange.between(0, 30).getInt(pRandom), true);
        } else {
            pItemStack.setCount(RandomValueRange.between(1, 3).getInt(pRandom));
        }
        Block.popResource(pServerLevel, pPos, pItemStack);
    }

    public static ItemStack getRandom(ITag.INamedTag<Item> pTag, Random pRandom) {
        return new ItemStack(pTag.getRandomElement(pRandom));
    }
}
