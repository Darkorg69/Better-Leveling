package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawOreLootModifier;
import darkorg.betterleveling.registry.GlobalLootModifiers;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(DataGenerator pGenerator) {
        super(pGenerator, BetterLeveling.MOD_ID);
    }

    @Override
    protected void start() {
        add("raw_iron", GlobalLootModifiers.RAW_ORE_LOOT.get(), new RawOreLootModifier(new ILootCondition[]{BlockStateProperty.hasBlockStateProperties(Blocks.IRON_ORE).build()}, ModItems.RAW_IRON.get()));
        add("raw_gold", GlobalLootModifiers.RAW_ORE_LOOT.get(), new RawOreLootModifier(new ILootCondition[]{BlockStateProperty.hasBlockStateProperties(Blocks.GOLD_ORE).build()}, ModItems.RAW_GOLD.get()));
        add("raw_debris", GlobalLootModifiers.RAW_ORE_LOOT.get(), new RawOreLootModifier(new ILootCondition[]{BlockStateProperty.hasBlockStateProperties(Blocks.ANCIENT_DEBRIS).build(),}, ModItems.RAW_DEBRIS.get()));
    }
}
