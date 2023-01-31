package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

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
    public void setOwner(Player pPlayer) {
        this.setUUID(pPlayer.getUUID());
    }

    @Override
    public boolean isOwner(Player pPlayer) {
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
    public CompoundTag getNBTData() {
        CompoundTag data = new CompoundTag();
        if (this.hasOwner()) {
            data.putUUID("Owner", this.uuid);
        }
        return data;
    }

    @Override
    public void setNBTData(CompoundTag pData) {
        if (pData.contains("Owner")) {
            this.uuid = pData.getUUID("Owner");
        }
    }
}
