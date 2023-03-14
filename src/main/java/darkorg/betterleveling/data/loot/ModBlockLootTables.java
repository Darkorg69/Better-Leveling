package darkorg.betterleveling.data.loot;

import darkorg.betterleveling.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.RAW_IRON_BLOCK.get());
        dropSelf(ModBlocks.RAW_GOLD_BLOCK.get());
        dropSelf(ModBlocks.RAW_DEBRIS_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
