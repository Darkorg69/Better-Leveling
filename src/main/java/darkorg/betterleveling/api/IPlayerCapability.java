package darkorg.betterleveling.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public interface IPlayerCapability {
    boolean canUnlock(PlayerEntity pPlayer);

    boolean hasUnlocked(PlayerEntity pPlayer);

    ISpecialization getFirstSpecialization(PlayerEntity pPlayer);

    List<ISpecialization> getSpecializations(PlayerEntity pPlayer);

    boolean getUnlocked(PlayerEntity pPlayer, ISpecialization pSpecialization);

    void setUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    void addUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked);

    boolean hasUnlocked(PlayerEntity pPlayer, ISkill pSkill);

    int getLevel(PlayerEntity pPlayer, ISkill pSkill);

    void setLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pLevel);

    void addLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pAmount);

    CompoundNBT getNBTData();

    void setNBTData(CompoundNBT pData);

    void sendDataToPlayer(ServerPlayerEntity pServerPlayer);

    void receiveDataFromServer(CompoundNBT pData);
}
