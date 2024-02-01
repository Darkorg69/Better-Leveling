package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput pPackOutput, ExistingFileHelper pExistingFileHelper) {
        super(pPackOutput, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithBlockItem(ModBlocks.RAW_DEBRIS_BLOCK.get());
    }

    private void simpleBlockWithBlockItem(Block pBlock) {
        simpleBlock(pBlock);
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(pBlock).getPath(), blockTexture(pBlock));
    }
}
