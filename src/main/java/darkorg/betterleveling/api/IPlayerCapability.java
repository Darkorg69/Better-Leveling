package darkorg.betterleveling.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IPlayerCapability {
    boolean canUnlock(Player pPlayer);

    boolean hasUnlocked(Player pPlayer);

    ISpecialization getFirstSpecialization(Player pPlayer);

    List<ISpecialization> getSpecializations(Player pPlayer);

    boolean getUnlocked(Player pPlayer, ISpecialization pSpecialization);

    void setUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    void addUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    boolean hasUnlocked(Player pPlayer, ISkill pSkill);

    int getLevel(Player pPlayer, ISkill pSkill);

    void setLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pLevel);

    void addLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pAmount);

    CompoundTag getNBTData();

    void setNBTData(CompoundTag pData);

    void sendDataToPlayer(ServerPlayer pServerPlayer);

    void receiveDataFromServer(CompoundTag pData);
}
