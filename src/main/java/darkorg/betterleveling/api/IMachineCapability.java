package darkorg.betterleveling.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public interface IMachineCapability {
    UUID getUUID();

    void setUUID(UUID pUUID);

    void setOwner(PlayerEntity pPlayerEntity);

    boolean isOwner(PlayerEntity pPlayerEntity);

    boolean hasOwner();

    void removeOwner();

    CompoundNBT getNBTData();

    void setNBTData(CompoundNBT pData);
}