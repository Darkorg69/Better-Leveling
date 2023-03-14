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
        public static final TagKey<Item> SKIN = createTag("skin");
        public static final TagKey<Item> MEAT = createTag("meat");
        public static final TagKey<Item> MEAT_RAW = createTag("meat/raw");
        public static final TagKey<Item> MEAT_COOKED = createTag("meat/cooked");
        public static final TagKey<Item> TREASURES_COMMON = createTag("treasures/common");
        public static final TagKey<Item> TREASURES_UNCOMMON = createTag("treasures/uncommon");
        public static final TagKey<Item> TREASURES_RARE = createTag("treasures/rare");

        private static TagKey<Item> createTag(String pName) {
            return ItemTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
