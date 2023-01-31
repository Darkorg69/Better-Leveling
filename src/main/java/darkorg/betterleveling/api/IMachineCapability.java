package darkorg.betterleveling.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface IMachineCapability {
    UUID getUUID();

    void setUUID(UUID pUUID);

    void setOwner(Player pPlayer);

    boolean isOwner(Player pPlayer);

    boolean hasOwner();

    void removeOwner();

    CompoundTag getNBTData();

    void setNBTData(CompoundTag pData);
}