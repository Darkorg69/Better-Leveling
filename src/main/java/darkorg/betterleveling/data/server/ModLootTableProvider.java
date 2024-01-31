package darkorg.betterleveling.data.server;

import darkorg.betterleveling.data.loot.ModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput pPackOutput) {
        super(pPackOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
    }
}