package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.ITileCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class TileCapability implements ITileCapability {
    private UUID owner;

    @Override
    public UUID getOwnerUUID() {
        return this.owner;
    }

    @Override
    public void setOwner(PlayerEntity pPlayer) {
        this.owner = PlayerEntity.getUUID(pPlayer.getGameProfile());
    }

    @Override
    public boolean isOwner(PlayerEntity pPlayer) {
        return this.owner == PlayerEntity.getUUID(pPlayer.getGameProfile());
    }

    @Override
    public void removeOwner() {
        this.owner = null;
    }

    @Override
    public boolean hasOwner() {
        return this.owner != null;
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT data = new CompoundNBT();

        if (this.hasOwner()) {
            data.putUniqueId("Owner", owner);
        }

        return data;
    }

    @Override
    public void readNBT(CompoundNBT pData) {
        if (pData.contains("Owner")) {
            this.owner = pData.getUniqueId("Owner");
        }
    }
}
