package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.capability.IBlockEntityCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class BlockEntityCapability implements IBlockEntityCapability {
    private UUID uuid;

    @Override
    public Player getOwner(ServerLevel pServerLevel) {
        return pServerLevel.getPlayerByUUID(this.uuid);
    }

    @Override
    public void setOwner(ServerPlayer pServerPlayer) {
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
    public boolean isOwner(ServerPlayer pServerPlayer) {
        return this.uuid == pServerPlayer.getUUID();
    }

    @Override
    public CompoundTag getData() {
        CompoundTag data = new CompoundTag();
        if (this.hasOwner()) {
            data.putUUID("Owner", this.uuid);
        }
        return data;
    }

    @Override
    public void setData(CompoundTag pData) {
        if (pData.hasUUID("Owner")) {
            this.uuid = pData.getUUID("Owner");
        }
    }
}
