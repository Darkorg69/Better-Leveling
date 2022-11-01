package darkorg.betterleveling.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public interface IPlayerCapability {
    boolean canUnlock(PlayerEntity pPlayer);

    boolean hasUnlocked(PlayerEntity pPlayer);

    ISpecialization getFirstUnlocked(PlayerEntity pPlayer);

    List<ISpecialization> getAllUnlocked(PlayerEntity pPlayer);

    boolean getUnlocked(PlayerEntity pPlayer, ISpecialization pSpecialization);

    void addUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    void setUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    boolean isUnlocked(PlayerEntity pPlayer, ISkill pSkill);

    int getLevel(PlayerEntity pPlayer, ISkill pSkill);

    void addLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pLevel);

    void setLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pLevel);

    CompoundNBT getData();

    void setData(CompoundNBT pData);

    void receiveDataFromServer(CompoundNBT pData);

    void sendDataToPlayer(ServerPlayerEntity pServerPlayer);
}
