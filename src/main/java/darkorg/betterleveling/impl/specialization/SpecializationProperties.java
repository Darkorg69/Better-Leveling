package darkorg.betterleveling.impl.specialization;

import darkorg.betterleveling.api.specialization.ISpecializationProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class SpecializationProperties implements ISpecializationProperties {
    private final ItemLike itemLike;
    private final IntValue levelCost;

    public SpecializationProperties(ItemLike pItemLike, IntValue pLevelCost) {
        this.itemLike = pItemLike;
        this.levelCost = pLevelCost;
    }

    @Override
    public int getLevelCost() {
        return this.levelCost.get();
    }

    @Override
    public ItemLike getItemLike() {
        return this.itemLike;
    }
}
