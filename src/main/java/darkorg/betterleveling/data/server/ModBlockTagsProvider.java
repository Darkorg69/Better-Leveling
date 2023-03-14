package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(Tags.Blocks.DIRT);
    }
}
