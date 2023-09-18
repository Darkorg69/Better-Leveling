package darkorg.betterleveling.data.server;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.CROPS_BEETROOT).add(Blocks.BEETROOTS);
        tag(ModTags.Blocks.CROPS_CARROT).add(Blocks.CARROTS);
        tag(ModTags.Blocks.CROPS_NETHER_WART).add(Blocks.NETHER_WART);
        tag(ModTags.Blocks.CROPS_POTATO).add(Blocks.POTATOES);
        tag(ModTags.Blocks.CROPS_WHEAT).add(Blocks.WHEAT);
        tag(ModTags.Blocks.CROPS).addTag(ModTags.Blocks.CROPS_BEETROOT).addTag(ModTags.Blocks.CROPS_CARROT).addTag(ModTags.Blocks.CROPS_POTATO).addTag(ModTags.Blocks.CROPS_WHEAT).addTag(ModTags.Blocks.CROPS_NETHER_WART);
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(BlockTags.DIRT);
    }
}
