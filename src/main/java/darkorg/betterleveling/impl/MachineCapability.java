package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MachineCapability implements IMachineCapability {
    private UUID ownerId;

    @Override
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Override
    public void setOwner(Player pPlayer) {
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
    public boolean isOwner(Player pPlayer) {
        return this.ownerId == pPlayer.getUUID();
    }

    @Override
    public CompoundTag getNBTData() {
        CompoundTag data = new CompoundTag();
        if (this.hasOwner()) {
            data.putUUID("Owner", ownerId);
        }
        return data;
    }

    @Override
    public void setNBTData(CompoundTag pData) {
        if (pData.contains("Owner")) {
            this.ownerId = pData.getUUID("Owner");
        }
    }
}
