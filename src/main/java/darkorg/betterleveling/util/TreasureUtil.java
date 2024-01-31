package darkorg.betterleveling.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.Random;

public class TreasureUtil {
    public static int getPotentialLoot(int pOriginalCount, double pPotentialLootBound) {
        return Math.toIntExact(Math.round(pOriginalCount * new Random().nextDouble(0.0D, pPotentialLootBound)));
    }

    public static void spawnTreasure(ServerLevel pServerLevel, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(pRandom.nextFloat(0.0F, 0.69F) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pServerLevel.random, pItemStack, pRandom.nextInt(30), true);
        } else {
            pItemStack.setCount(pRandom.nextInt(1, 3));
        }
        Block.popResource(pServerLevel, pPos, pItemStack);
    }

    public static ItemStack getRandomTreasure(TagKey<Item> pTag, RandomSource pRandom) {
        ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();

        if (tags != null) {
            return new ItemStack(tags.getTag(pTag).getRandomElement(pRandom).orElseThrow());
        }

        return ItemStack.EMPTY;
    }
}
