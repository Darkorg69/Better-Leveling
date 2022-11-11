package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("unchecked")
public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, String modId, ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.CROPS).add(Blocks.COCOA, Blocks.NETHER_WART).addTag(BlockTags.CROPS);
        tag(ModTags.Blocks.ORES).addTags(Tags.Blocks.ORES_COAL, Tags.Blocks.ORES_DIAMOND, Tags.Blocks.ORES_EMERALD, Tags.Blocks.ORES_LAPIS, Tags.Blocks.ORES_QUARTZ, Tags.Blocks.ORES_REDSTONE);
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(Tags.Blocks.DIRT);
    }
}
