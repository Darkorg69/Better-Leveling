package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, String modId, ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.CROPS).add(Blocks.COCOA, Blocks.NETHER_WART).addTag(BlockTags.CROPS);
        tag(ModTags.Blocks.ORES).addTag(Tags.Blocks.ORES);
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(BlockTags.DIRT);
    }
}
