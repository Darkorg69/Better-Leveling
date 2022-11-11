package darkorg.betterleveling.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface IMachineCapability {
    UUID getOwnerId();

    void setOwner(Player pPlayer);

    void removeOwner();

    boolean hasOwner();

    boolean isOwner(Player pPlayer);

    CompoundTag getNBTData();

    void setNBTData(CompoundTag pData);
}