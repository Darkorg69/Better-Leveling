package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class MachineCapability implements IMachineCapability {
    private UUID ownerId;

    @Override
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Override
    public void setOwner(PlayerEntity pPlayer) {
        this.ownerId = pPlayer.getUUID();
    }

    @Override
    public void removeOwner() {
        this.ownerId = null;
    }

    @Override
    public boolean hasOwner() {
        return this.ownerId != null;
    }

    @Override
    public boolean isOwner(PlayerEntity pPlayer) {
        return this.ownerId == pPlayer.getUUID();
    }

    @Override
    public CompoundNBT getNBTData() {
        CompoundNBT data = new CompoundNBT();
        if (this.hasOwner()) {
            data.putUUID("Owner", ownerId);
        }
        return data;
    }

    @Override
    public void setNBTData(CompoundNBT pData) {
        if (pData.contains("Owner")) {
            this.ownerId = pData.getUUID("Owner");
        }
    }
}
