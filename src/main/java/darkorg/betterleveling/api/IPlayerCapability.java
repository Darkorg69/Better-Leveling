package darkorg.betterleveling.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IPlayerCapability {
    boolean canUnlock(Player pPlayer);

    boolean hasUnlocked(Player pPlayer);

    ISpecialization getFirstUnlocked(Player pPlayer);

    List<ISpecialization> getAllUnlocked(Player pPlayer);

    boolean getUnlocked(Player pPlayer, ISpecialization pSpecialization);

    void addUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    void setUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    boolean isUnlocked(Player pPlayer, ISkill pSkill);

    int getLevel(Player pPlayer, ISkill pSkill);

    void addLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pLevel);

    void setLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pLevel);

    CompoundTag getNBTData();

    void setNBTData(CompoundTag pData);

    void resetPlayer(ServerPlayer pServerPlayer);

    void receiveDataFromServer(CompoundTag pData);

    void sendDataToPlayer(ServerPlayer pServerPlayer);
}
