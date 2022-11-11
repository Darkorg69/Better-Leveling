package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.impl.Specialization;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecRegistry {
    public static final ISpecialization COMBAT = new Specialization(BetterLeveling.MOD_ID, "combat", Items.STONE_SWORD);
    public static final ISpecialization CRAFTING = new Specialization(BetterLeveling.MOD_ID, "crafting", Items.CRAFTING_TABLE);
    public static final ISpecialization MINING = new Specialization(BetterLeveling.MOD_ID, "mining", Items.STONE_PICKAXE);
    private static final List<ISpecialization> SPEC_REGISTRY = new ArrayList<>();
    private static final Map<String, ISpecialization> SPEC_NAME_MAP = new HashMap<>();

    public static void init() {
        registerSpecialization(COMBAT);
        registerSpecialization(CRAFTING);
        registerSpecialization(MINING);
    }

    public static void registerSpecialization(ISpecialization pPlayerClass) {
        SPEC_REGISTRY.add(pPlayerClass);
        SPEC_NAME_MAP.put(pPlayerClass.getName(), pPlayerClass);
    }

    public static List<ISpecialization> getSpecRegistry() {
        return SPEC_REGISTRY;
    }

    public static Map<String, ISpecialization> getSpecNameMap() {
        return SPEC_NAME_MAP;
    }
}
