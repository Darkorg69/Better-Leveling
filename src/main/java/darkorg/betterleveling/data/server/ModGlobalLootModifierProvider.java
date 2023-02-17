package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawDebrisLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput pOutput) {
        super(pOutput, BetterLeveling.MOD_ID);
    }

    @Override
    protected void start() {
        add("ancient_debris", new RawDebrisLootModifier(new LootItemCondition[]{LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.ANCIENT_DEBRIS).build()}));
    }
}
