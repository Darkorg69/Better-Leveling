package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator pDataGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pDataGenerator, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithBlockItem(ModBlocks.RAW_IRON_BLOCK.get());
        simpleBlockWithBlockItem(ModBlocks.RAW_GOLD_BLOCK.get());
        simpleBlockWithBlockItem(ModBlocks.RAW_DEBRIS_BLOCK.get());
    }

    private void simpleBlockWithBlockItem(Block pBlock) {
        simpleBlock(pBlock);
        itemModels().withExistingParent(getName(pBlock), blockTexture(pBlock));
    }

    private String getName(Block pBlock) {
        return pBlock.getRegistryName() != null ? pBlock.getRegistryName().getPath() : null;
    }
}
