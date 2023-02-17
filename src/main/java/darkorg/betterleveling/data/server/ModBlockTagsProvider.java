package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper pExistingFileHelper) {
        super(pOutput, pLookupProvider, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider pProvider) {
        tag(ModTags.Blocks.CROPS_BEETROOT).add(Blocks.BEETROOTS);
        tag(ModTags.Blocks.CROPS_CARROT).add(Blocks.CARROTS);
        tag(ModTags.Blocks.CROPS_NETHER_WART).add(Blocks.NETHER_WART);
        tag(ModTags.Blocks.CROPS_POTATO).add(Blocks.POTATOES);
        tag(ModTags.Blocks.CROPS_WHEAT).add(Blocks.WHEAT);
        tag(ModTags.Blocks.CROPS).addTag(ModTags.Blocks.CROPS_BEETROOT).addTag(ModTags.Blocks.CROPS_CARROT).addTag(ModTags.Blocks.CROPS_POTATO).addTag(ModTags.Blocks.CROPS_WHEAT).addTag(ModTags.Blocks.CROPS_NETHER_WART);
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(BlockTags.DIRT);
    }
}
