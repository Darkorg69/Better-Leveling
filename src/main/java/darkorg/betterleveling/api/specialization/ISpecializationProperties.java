package darkorg.betterleveling.api.specialization;

import net.minecraft.util.IItemProvider;

public interface ISpecializationProperties {
    int getLevelCost();

    IItemProvider getItemLike();
}
