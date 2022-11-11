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
        public static final TagKey<Block> CROPS = createTag("crops");
        public static final TagKey<Block> ORES = createTag("ores");
        public static final TagKey<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static TagKey<Block> createTag(String pName) {
            return BlockTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }

    public static class Items {
        public static final TagKey<Item> ANIMAL_MEAT = createTag("animal_meat");
        public static final TagKey<Item> ANIMAL_SKIN = createTag("animal_skin");
        public static final TagKey<Item> CROPS = createTag("crops");
        public static final TagKey<Item> ORES = createTag("ores");
        public static final TagKey<Item> TREASURE_COMMON = createTag("treasure_common");
        public static final TagKey<Item> TREASURE_RARE = createTag("treasure_rare");
        public static final TagKey<Item> TREASURE_UNCOMMON = createTag("treasure_uncommon");

        private static TagKey<Item> createTag(String pName) {
            return ItemTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
