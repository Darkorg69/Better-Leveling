package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static TagKey<Block> createTag(String pName) {
            return BlockTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }

    public static class Items {
        public static final TagKey<Item> MEATS = createTag("meats");
        public static final TagKey<Item> SKINS = createTag("skins");
        public static final TagKey<Item> CROPS = createTag("crops");
        public static final TagKey<Item> ORES = createTag("ores");
        public static final TagKey<Item> COMMON_TREASURES = createTag("common_treasures");
        public static final TagKey<Item> UNCOMMON_TREASURES = createTag("uncommon_treasures");
        public static final TagKey<Item> RARE_TREASURES = createTag("rare_treasures");

        private static TagKey<Item> createTag(String pName) {
            return ItemTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
