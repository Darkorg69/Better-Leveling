package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.copy(ModTags.Blocks.TREASURE_BLOCKS, ModTags.Items.TREASURE_BLOCKS);
        this.tag(ModTags.Items.COMMON_LOOT).add(Items.ARROW, Items.CLAY_BALL, Items.GLASS_BOTTLE, Items.STICK);
        this.tag(ModTags.Items.CROPS).add(Items.BEETROOT, Items.COCOA_BEANS, Items.NETHER_WART, Items.POTATO, Items.SWEET_BERRIES, Items.WHEAT);
        this.tag(ModTags.Items.MEATS).add(Items.BEEF, Items.MUTTON, Items.PORKCHOP, Items.ROTTEN_FLESH);
        this.tag(ModTags.Items.ORES).add(Items.COAL, Items.DIAMOND, Items.EMERALD, Items.LAPIS_ORE, Items.GOLD_NUGGET, Items.QUARTZ, Items.REDSTONE);
        this.tag(ModTags.Items.RARE_LOOT).add(Items.DIAMOND, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL);
        this.tag(ModTags.Items.SKINS).add(Items.LEATHER, Items.RABBIT_HIDE).addTag(ItemTags.WOOL);
        this.tag(ModTags.Items.UNCOMMON_LOOT).add(Items.GOLD_NUGGET, Items.IRON_NUGGET);
    }
}