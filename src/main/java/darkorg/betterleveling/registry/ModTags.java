package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> CROPS = createTag("crops");
        public static final Tags.IOptionalNamedTag<Block> ORES = createTag("ores");
        public static final Tags.IOptionalNamedTag<Block> TREASURE_BLOCKS = createTag("treasure_blocks");

        private static Tags.IOptionalNamedTag<Block> createTag(String pName) {
            return BlockTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }

    public static class Items {
        public static final Tags.IOptionalNamedTag<Item> COMMON_LOOT = createTag("common_loot");
        public static final Tags.IOptionalNamedTag<Item> CROPS = createTag("crops");
        public static final Tags.IOptionalNamedTag<Item> MEATS = createTag("meats");
        public static final Tags.IOptionalNamedTag<Item> ORES = createTag("ores");
        public static final Tags.IOptionalNamedTag<Item> RARE_LOOT = createTag("rare_loot");
        public static final Tags.IOptionalNamedTag<Item> SKINS = createTag("skins");
        public static final Tags.IOptionalNamedTag<Item> TREASURE_BLOCKS = createTag("treasure_blocks");
        public static final Tags.IOptionalNamedTag<Item> UNCOMMON_LOOT = createTag("uncommon_loot");

        private static Tags.IOptionalNamedTag<Item> createTag(String pName) {
            return ItemTags.createOptional(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
