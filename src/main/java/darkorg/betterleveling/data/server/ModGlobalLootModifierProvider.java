package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawOreLootModifier;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput pPackOutput) {
        super(pPackOutput, BetterLeveling.MOD_ID);
    }

    @Override
    protected void start() {
        add("raw_debris", new RawOreLootModifier(new LootItemCondition[]{LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.ANCIENT_DEBRIS).build()}, ModItems.RAW_DEBRIS.get()));
    }
}
