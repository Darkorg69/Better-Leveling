package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.capability.IPlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.HashMap;
import java.util.Map;

public class PlayerCapability implements IPlayerCapability {
    private final Map<Specialization, Boolean> specializations;
    private final Map<Skill, Integer> skills;
    private Specialization specialization;
    private int availableExperience;
    private CompoundNBT data;

    public PlayerCapability() {
        this.specializations = new HashMap<>();
        this.skills = new HashMap<>();
        this.specialization = null;
        this.availableExperience = 0;

        this.data = new CompoundNBT();
        this.data.put("Specs", new ListNBT());
        this.data.put("Skills", new ListNBT());
        this.data.put("Spec", new CompoundNBT());

        Specializations.getAll().forEach(specialization -> this.putSpecialization(specialization, false));
        Skills.getAll().forEach(skill -> this.putSkill(skill, 0));
        this.putAvailableExperience(0);
    }

    @Override
    public boolean getUnlocked(PlayerEntity pPlayer, Specialization pSpecialization) {
        if (pPlayer.level.isClientSide) {
            return this.specializations.getOrDefault(pSpecialization, false);
        }

        for (INBT entry : this.data.getList("Specs", 10)) {
            CompoundNBT spec = (CompoundNBT) entry;
            if (spec.getString("Name").equals(pSpecialization.getName())) {
                return spec.getBoolean("Unlocked");
            }
        }

        return false;
    }

    @Override
    public void setUnlocked(ServerPlayerEntity pServerPlayer, Specialization pSpecialization, boolean pUnlocked) {
        this.putSpecialization(pSpecialization, pUnlocked);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public int getLevel(PlayerEntity pPlayer, Skill pSkill) {
        if (pPlayer.level.isClientSide) {
            return this.skills.getOrDefault(pSkill, 0);
        }

        for (INBT entry : this.data.getList("Skills", 10)) {
            CompoundNBT skill = (CompoundNBT) entry;
            if (skill.getString("Name").equals(pSkill.getName())) {
                return skill.getInt("Level");
            }
        }

        return 0;
    }

    @Override
    public void setLevel(ServerPlayerEntity pServerPlayer, Skill pSkill, int pLevel) {
        this.putSkill(pSkill, Math.min(pLevel, pSkill.getProperties().getMaxLevel()));
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayerEntity pServerPlayer, Skill pSkill, int pLevel) {
        this.setLevel(pServerPlayer, pSkill, getLevel(pServerPlayer, pSkill) + pLevel);
    }

    @Override
    public Specialization getSpecialization(PlayerEntity pPlayer) {
        if (pPlayer.level.isClientSide) {
            return this.specialization;
        }

        String name = this.data.getCompound("Spec").getString("Name");
        return name.isEmpty() ? null : Specializations.getFrom(name);
    }

    @Override
    public void setSpecialization(ServerPlayerEntity pServerPlayer, Specialization pSpecialization) {
        this.putSpecialization(pSpecialization);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public int getAvailableExperience(PlayerEntity pPlayer) {
        if (pPlayer.level.isClientSide) {
            return this.availableExperience;
        }

        return this.data.getInt("AvailableXP");
    }

    @Override
    public void setAvailableExperience(ServerPlayerEntity pServerPlayer, int pAmount) {
        this.putAvailableExperience(pAmount);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void updateAvailableExperience(ServerPlayerEntity pServerPlayer) {
        this.setAvailableExperience(pServerPlayer, PlayerUtil.getAvailableExperience(pServerPlayer));
    }

    @Override
    public void sendDataToPlayer(ServerPlayerEntity pServerPlayer) {
        NetworkHandler.sendToPlayer(new SyncDataS2CPacket(this.data), pServerPlayer);
    }

    @Override
    public void receiveDataFromServer(CompoundNBT pData) {
        for (INBT entry : pData.getList("Specs", 10)) {
            CompoundNBT specialization = (CompoundNBT) entry;
            this.specializations.put(Specializations.getFrom(specialization.getString("Name")), specialization.getBoolean("Unlocked"));
        }

        for (INBT entry : pData.getList("Skills", 10)) {
            CompoundNBT skill = (CompoundNBT) entry;
            this.skills.put(Skills.getFrom(skill.getString("Name")), skill.getInt("Level"));
        }

        String name = pData.getCompound("Spec").getString("Name");
        this.specialization = name.isEmpty() ? null : Specializations.getFrom(name);

        this.availableExperience = pData.getInt("AvailableXP");
    }

    @Override
    public CompoundNBT getData() {
        return this.data;
    }

    @Override
    public void setData(CompoundNBT pData) {
        this.data = pData;
    }

    private void putSpecialization(Specialization pSpecialization, boolean pUnlocked) {
        ListNBT specs = this.data.getList("Specs", 10);

        CompoundNBT spec = new CompoundNBT();
        spec.putString("Name", pSpecialization.getName());
        spec.putBoolean("Unlocked", pUnlocked);

        if (specs.isEmpty()) {
            specs.add(spec);
            return;
        }

        for (INBT entry : specs) {
            CompoundNBT nbt = (CompoundNBT) entry;
            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                nbt.putBoolean("Unlocked", pUnlocked);
                return;
            }
        }

        specs.add(spec);
    }

    private void putSkill(Skill pSkill, int pLevel) {
        ListNBT skills = this.data.getList("Skills", 10);

        CompoundNBT skill = new CompoundNBT();
        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);

        if (skills.isEmpty()) {
            skills.add(skill);
            return;
        }

        for (INBT entry : skills) {
            CompoundNBT nbt = (CompoundNBT) entry;
            if (nbt.getString("Name").equals(pSkill.getName())) {
                nbt.putInt("Level", Math.max(0, pLevel));
                return;
            }
        }

        skills.add(skill);
    }

    private void putSpecialization(Specialization pSpecialization) {
        CompoundNBT spec = new CompoundNBT();

        if (pSpecialization != null) {
            spec.putString("Name", pSpecialization.getName());
        }

        this.data.put("Spec", spec);
    }

    private void putAvailableExperience(int pAvailableXP) {
        this.data.putInt("AvailableXP", pAvailableXP);
    }
}
