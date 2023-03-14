package darkorg.betterleveling.api.capability;

import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface IPlayerCapability {
    boolean getUnlocked(Player pPlayer, Specialization pSpecialization);

    void setUnlocked(ServerPlayer pServerPlayer, Specialization pSpecialization, boolean pUnlocked);

    int getLevel(Player pPlayer, Skill pSkill);

    void setLevel(ServerPlayer pServerPlayer, Skill pSkill, int pLevel);

    void addLevel(ServerPlayer pServerPlayer, Skill pSkill, int pAmount);

    int getAvailableExperience(Player pPlayer);

    void setAvailableExperience(ServerPlayer pServerPlayer, int pAmount);

    void updateAvailableExperience(ServerPlayer pServerPlayer);

    Specialization getSpecialization(Player pPlayer);

    void setSpecialization(ServerPlayer pServerPlayer, Specialization pSpecialization);

    void sendDataToPlayer(ServerPlayer pServerPlayer);

    void receiveDataFromServer(CompoundTag pData);

    CompoundTag getData();

    void setData(CompoundTag pData);
}
