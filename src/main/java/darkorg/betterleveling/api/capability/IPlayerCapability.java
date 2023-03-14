package darkorg.betterleveling.api.capability;

import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IPlayerCapability {
    boolean getUnlocked(PlayerEntity pPlayer, Specialization pSpecialization);

    void setUnlocked(ServerPlayerEntity pServerPlayer, Specialization pSpecialization, boolean pUnlocked);

    int getLevel(PlayerEntity pPlayer, Skill pSkill);

    void setLevel(ServerPlayerEntity pServerPlayer, Skill pSkill, int pLevel);

    void addLevel(ServerPlayerEntity pServerPlayer, Skill pSkill, int pAmount);

    int getAvailableExperience(PlayerEntity pPlayer);

    void setAvailableExperience(ServerPlayerEntity pServerPlayer, int pAmount);

    void updateAvailableExperience(ServerPlayerEntity pServerPlayer);

    Specialization getSpecialization(PlayerEntity pPlayer);

    void setSpecialization(ServerPlayerEntity pServerPlayer, Specialization pSpecialization);

    void sendDataToPlayer(ServerPlayerEntity pServerPlayer);

    void receiveDataFromServer(CompoundNBT pData);

    CompoundNBT getData();

    void setData(CompoundNBT pData);
}
