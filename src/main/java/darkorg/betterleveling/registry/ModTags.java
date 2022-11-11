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
        public static final ITag.INamedTag<Block> CROPS = createTag("crops");
        public static final ITag.INamedTag<Block> ORES = createTag("ores");
        public static final ITag.INamedTag<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static ITag.INamedTag<Block> createTag(String pName) {
            return BlockTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }

    public static class Items {
        public static final ITag.INamedTag<Item> ANIMAL_MEAT = createTag("animal_meat");
        public static final ITag.INamedTag<Item> ANIMAL_SKIN = createTag("animal_skin");
        public static final ITag.INamedTag<Item> CROPS = createTag("crops");
        public static final ITag.INamedTag<Item> ORES = createTag("ores");
        public static final ITag.INamedTag<Item> TREASURE_COMMON = createTag("treasure_common");
        public static final ITag.INamedTag<Item> TREASURE_RARE = createTag("treasure_rare");
        public static final ITag.INamedTag<Item> TREASURE_UNCOMMON = createTag("treasure_uncommon");

        private static ITag.INamedTag<Item> createTag(String pName) {
            return ItemTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
