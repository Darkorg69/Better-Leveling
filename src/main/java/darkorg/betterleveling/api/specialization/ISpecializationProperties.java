package darkorg.betterleveling.api.specialization;

import net.minecraft.world.level.ItemLike;

public interface ISpecializationProperties {
    int getLevelCost();

    ItemLike getItemLike();
}
