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
        public static final TagKey<Block> CROPS_BEETROOT = createTag("crops/beetroot");
        public static final TagKey<Block> CROPS_CARROT = createTag("crops/carrot");
        public static final TagKey<Block> CROPS_NETHER_WART = createTag("crops/nether_wart");
        public static final TagKey<Block> CROPS_POTATO = createTag("crops/potato");
        public static final TagKey<Block> CROPS_WHEAT = createTag("crops/wheat");
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
        public static final TagKey<Item> TREASURES_RARE = createTag("treasures/rare");
        public static final TagKey<Item> TREASURES_COMMON = createTag("treasures/common");
        public static final TagKey<Item> TREASURES_UNCOMMON = createTag("treasures/uncommon");
        public static final TagKey<Item> RESOURCES = createTag("resources");
        public static final TagKey<Item> RESOURCES_COAL = createTag("resources/coal");
        public static final TagKey<Item> RESOURCES_COPPER = createTag("resources/copper");
        public static final TagKey<Item> RESOURCES_DIAMOND = createTag("resources/diamond");
        public static final TagKey<Item> RESOURCES_EMERALD = createTag("resources/emerald");
        public static final TagKey<Item> RESOURCES_GOLD = createTag("resources/gold");
        public static final TagKey<Item> RESOURCES_IRON = createTag("resources/iron");
        public static final TagKey<Item> RESOURCES_LAPIS = createTag("resources/lapis_lazuli");
        public static final TagKey<Item> RESOURCES_NETHERITE_SCRAP = createTag("resources/netherite_scrap");
        public static final TagKey<Item> RESOURCES_QUARTZ = createTag("resources/quartz");
        public static final TagKey<Item> RESOURCES_REDSTONE = createTag("resources/redstone");

        private static TagKey<Item> createTag(String pName) {
            return ItemTags.create(new ResourceLocation(BetterLeveling.MOD_ID, pName));
        }
    }
}
