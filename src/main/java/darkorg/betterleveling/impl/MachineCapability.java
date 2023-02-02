package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class MachineCapability implements IMachineCapability {
    private UUID uuid;

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void setUUID(UUID pUUID) {
        this.uuid = pUUID;
    }

    @Override
    public void setOwner(PlayerEntity pPlayer) {
        this.setUUID(pPlayer.getUUID());
    }

    @Override
    public boolean isOwner(PlayerEntity pPlayer) {
        return this.uuid == pPlayer.getUUID();
    }

    @Override
    public boolean hasOwner() {
        return this.uuid != null;
    }

    @Override
    public void removeOwner() {
        this.setUUID(null);
    }

    @Override
    public CompoundNBT getNBTData() {
        CompoundNBT data = new CompoundNBT();
        if (this.hasOwner()) {
            data.putUUID("Owner", this.uuid);
        }
        return data;
    }

    @Override
    public void setNBTData(CompoundNBT pData) {
        if (pData.contains("Owner")) {
            this.uuid = pData.getUUID("Owner");
        }
    }
}
