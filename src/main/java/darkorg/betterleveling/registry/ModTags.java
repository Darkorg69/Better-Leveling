package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static class Blocks {
        public static final ITag.INamedTag<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static ITag.INamedTag<Block> createTag(String pName) {
            return BlockTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }

    }

    public static class Items {
        public static final ITag.INamedTag<Item> MEATS = createTag("meats");
        public static final ITag.INamedTag<Item> SKINS = createTag("skins");
        public static final ITag.INamedTag<Item> CROPS = createTag("crops");
        public static final ITag.INamedTag<Item> ORES = createTag("ores");
        public static final ITag.INamedTag<Item> COMMON_TREASURES = createTag("common_treasures");
        public static final ITag.INamedTag<Item> UNCOMMON_TREASURES = createTag("uncommon_treasures");
        public static final ITag.INamedTag<Item> RARE_TREASURES = createTag("rare_treasures");

        private static ITag.INamedTag<Item> createTag(String pName) {
            return ItemTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }

    }
}
