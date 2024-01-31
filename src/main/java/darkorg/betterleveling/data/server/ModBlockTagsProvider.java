package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModBlocks;
import darkorg.betterleveling.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper pExistingFileHelper) {
        super(pPackOutput, pLookupProvider, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider pLookupProvider) {
        tag(ModTags.Blocks.TREASURE_BLOCKS).addTag(BlockTags.DIRT);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.RAW_DEBRIS_BLOCK.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.RAW_DEBRIS_BLOCK.get());
    }
}
