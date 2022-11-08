package darkorg.betterleveling.data;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, String modId, ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.Blocks.CROPS).add(Blocks.BEETROOTS, Blocks.COCOA, Blocks.NETHER_WART, Blocks.POTATOES, Blocks.WHEAT);
        this.tag(ModTags.Blocks.ORES).add(Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE, Blocks.REDSTONE_ORE);
        this.tag(ModTags.Blocks.TREASURE_BLOCKS).add(Blocks.DIRT);
    }
}
