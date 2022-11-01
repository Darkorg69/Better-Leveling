package darkorg.betterleveling.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public interface ITileCapability {
    UUID getOwnerUUID();

    void setOwner(PlayerEntity pPlayer);

    boolean isOwner(PlayerEntity pPlayer);

    void removeOwner();

    boolean hasOwner();

    CompoundNBT writeNBT();

    void readNBT(CompoundNBT pData);
}