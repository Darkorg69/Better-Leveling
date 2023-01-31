package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("unchecked")
public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator pDataGenerator, BlockTagsProvider pBlockTagsProvider, String pModId, ExistingFileHelper pExistingFileHelper) {
        super(pDataGenerator, pBlockTagsProvider, pModId, pExistingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Items.CROPS).add(Items.COCOA_BEANS, Items.SWEET_BERRIES).addTags(Tags.Items.CROPS);
        tag(ModTags.Items.SKINS).add(Items.RABBIT_HIDE).addTags(ItemTags.WOOL, Tags.Items.FEATHERS, Tags.Items.LEATHER);
        tag(ModTags.Items.MEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, Items.ROTTEN_FLESH);
        tag(ModTags.Items.ORES).addTags(ItemTags.COALS, Tags.Items.DUSTS_REDSTONE, Tags.Items.GEMS, Tags.Items.NUGGETS, Tags.Items.RAW_MATERIALS);
        tag(ModTags.Items.COMMON_TREASURES).add(Items.CLAY_BALL).addTags(Tags.Items.BONES, Tags.Items.STRING);
        tag(ModTags.Items.UNCOMMON_TREASURES).addTags(ItemTags.ARROWS, ItemTags.COALS, Tags.Items.DUSTS);
        tag(ModTags.Items.RARE_TREASURES).addTags(Tags.Items.GEMS, Tags.Items.NUGGETS, Tags.Items.RODS);
    }
}