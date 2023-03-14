package darkorg.betterleveling.api.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface IBlockEntityCapability {
    Player getOwner(ServerLevel pServerLevel);

    void setOwner(ServerPlayer pServerPlayer);

    void removeOwner();

    boolean hasOwner();

    boolean isOwner(ServerPlayer pServerPlayer);

    CompoundTag getData();

    void setData(CompoundTag pData);
}