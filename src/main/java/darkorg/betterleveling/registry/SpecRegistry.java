package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.impl.Specialization;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecRegistry {
    public static final ISpecialization COMBAT = createSpecialization("combat", Items.STONE_SWORD);


    public static final ISpecialization CRAFTING = createSpecialization("crafting", Items.CRAFTING_TABLE);
    public static final ISpecialization MINING = createSpecialization("mining", Items.STONE_PICKAXE);

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

    public static ImmutableList<ISpecialization> getSpecRegistry() {
        return ImmutableList.copyOf(SPEC_REGISTRY);
    }

    public static ImmutableMap<String, ISpecialization> getSpecNameMap() {
        return ImmutableMap.copyOf(SPEC_NAME_MAP);
    }

    private static Specialization createSpecialization(String pName, ItemLike pItemLike) {
        return new Specialization(BetterLeveling.MOD_ID, pName, pItemLike);
    }
}
