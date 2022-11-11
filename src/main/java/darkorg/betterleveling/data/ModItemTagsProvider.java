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
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Items.ANIMAL_MEAT).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, Items.ROTTEN_FLESH);
        tag(ModTags.Items.ANIMAL_SKIN).add(Items.FEATHER, Items.RABBIT_HIDE).addTags(ItemTags.WOOL, Tags.Items.LEATHER);
        tag(ModTags.Items.CROPS).add(Items.COCOA_BEANS, Items.SWEET_BERRIES).addTags(Tags.Items.CROPS);
        tag(ModTags.Items.ORES).add(Items.COAL, Items.REDSTONE).addTags(Tags.Items.GEMS, Tags.Items.NUGGETS);
        tag(ModTags.Items.TREASURE_COMMON).add(Items.CLAY_BALL).addTags(Tags.Items.STRING);
        tag(ModTags.Items.TREASURE_RARE).add(Items.GOLDEN_SHOVEL).addTags(Tags.Items.GEMS, Tags.Items.RODS);
        tag(ModTags.Items.TREASURE_UNCOMMON).add(Items.ARROW).addTags(Tags.Items.DUSTS, Tags.Items.NUGGETS);
    }
}