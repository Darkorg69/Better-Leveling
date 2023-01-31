package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator pDataGenerator, String pModId, ExistingFileHelper pExistingFileHelper) {
        super(pDataGenerator, pModId, pExistingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(BlockTags.DIRT);
    }
}
