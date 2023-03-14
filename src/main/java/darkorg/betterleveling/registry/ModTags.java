package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class ModTags {
    public static class Blocks {
        public static final IOptionalNamedTag<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static IOptionalNamedTag<Block> createTag(String pName) {
            return BlockTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }

    public static class Items {
        public static final IOptionalNamedTag<Item> SKIN = createTag("skin");
        public static final IOptionalNamedTag<Item> MEAT = createTag("meat");
        public static final IOptionalNamedTag<Item> MEAT_RAW = createTag("meat/raw");
        public static final IOptionalNamedTag<Item> MEAT_COOKED = createTag("meat/cooked");
        public static final IOptionalNamedTag<Item> TREASURES_COMMON = createTag("treasures/common");
        public static final IOptionalNamedTag<Item> TREASURES_UNCOMMON = createTag("treasures/uncommon");
        public static final IOptionalNamedTag<Item> TREASURES_RARE = createTag("treasures/rare");

        private static IOptionalNamedTag<Item> createTag(String pName) {
            return ItemTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
