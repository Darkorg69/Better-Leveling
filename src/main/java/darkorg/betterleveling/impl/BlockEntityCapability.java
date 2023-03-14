package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.capability.IBlockEntityCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class BlockEntityCapability implements IBlockEntityCapability {
    private UUID uuid;

    @Override
    public PlayerEntity getOwner(ServerWorld pServerLevel) {
        return pServerLevel.getPlayerByUUID(this.uuid);
    }

    @Override
    public void setOwner(ServerPlayerEntity pServerPlayer) {
        this.uuid = pServerPlayer.getUUID();
    }

    @Override
    public void removeOwner() {
        this.uuid = null;
    }

    @Override
    public boolean hasOwner() {
        return this.uuid != null;
    }

    @Override
    public boolean isOwner(ServerPlayerEntity pServerPlayer) {
        return this.uuid == pServerPlayer.getUUID();
    }

    @Override
    public CompoundNBT getData() {
        CompoundNBT data = new CompoundNBT();
        if (this.hasOwner()) {
            data.putUUID("Owner", this.uuid);
        }
        return data;
    }

    @Override
    public void setData(CompoundNBT pData) {
        if (pData.hasUUID("Owner")) {
            this.uuid = pData.getUUID("Owner");
        }
    }
}
