package darkorg.betterleveling.impl.specialization;

import darkorg.betterleveling.api.specialization.ISpecializationProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class SpecializationProperties implements ISpecializationProperties {
    private final IItemProvider itemLike;
    private final IntValue levelCost;

    public SpecializationProperties(IItemProvider pItemLike, IntValue pLevelCost) {
        this.itemLike = pItemLike;
        this.levelCost = pLevelCost;
    }

    @Override
    public int getLevelCost() {
        return this.levelCost.get();
    }

    @Override
    public IItemProvider getItemLike() {
        return this.itemLike;
    }
}
