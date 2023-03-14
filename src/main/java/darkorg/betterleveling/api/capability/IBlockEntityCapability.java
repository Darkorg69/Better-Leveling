package darkorg.betterleveling.api.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public interface IBlockEntityCapability {
    PlayerEntity getOwner(ServerWorld pServerLevel);

    void setOwner(ServerPlayerEntity pServerPlayer);

    void removeOwner();

    boolean hasOwner();

    boolean isOwner(ServerPlayerEntity pServerPlayer);

    CompoundNBT getData();

    void setData(CompoundNBT pData);
}