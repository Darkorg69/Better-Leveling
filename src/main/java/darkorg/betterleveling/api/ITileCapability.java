package darkorg.betterleveling.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public interface ITileCapability {

    UUID getOwnerId();

    void setOwner(PlayerEntity pPlayer);

    void removeOwner();

    boolean hasOwner();

    boolean isOwner(PlayerEntity pPlayer);

    CompoundNBT writeNBT();

    void readNBT(CompoundNBT pData);
}