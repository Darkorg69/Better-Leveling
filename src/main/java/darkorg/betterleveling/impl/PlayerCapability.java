package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.capability.IPlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.PlayerUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerCapability implements IPlayerCapability {
    private final Map<Specialization, Boolean> specializations;
    private final Map<Skill, Integer> skills;
    private Specialization specialization;
    private int availableExperience;
    private CompoundTag data;

    public PlayerCapability() {
        this.specializations = new HashMap<>();
        this.skills = new HashMap<>();
        this.specialization = null;
        this.availableExperience = 0;

        this.data = new CompoundTag();
        this.data.put("Specs", new ListTag());
        this.data.put("Skills", new ListTag());
        this.data.put("Spec", new CompoundTag());

        Specializations.getAll().forEach(specialization -> this.putSpecialization(specialization, false));
        Skills.getAll().forEach(skill -> this.putSkill(skill, 0));
        this.putAvailableExperience(0);
    }

    @Override
    public boolean getUnlocked(Player pPlayer, Specialization pSpecialization) {
        if (pPlayer.level().isClientSide) {
            return this.specializations.getOrDefault(pSpecialization, false);
        }

        for (Tag entry : this.data.getList("Specs", 10)) {
            CompoundTag spec = (CompoundTag) entry;
            if (spec.getString("Name").equals(pSpecialization.getName())) {
                return spec.getBoolean("Unlocked");
            }
        }

        return false;
    }

    @Override
    public void setUnlocked(ServerPlayer pServerPlayer, Specialization pSpecialization, boolean pUnlocked) {
        this.putSpecialization(pSpecialization, pUnlocked);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public int getLevel(Player pPlayer, Skill pSkill) {
        if (pPlayer.level().isClientSide) {
            return this.skills.getOrDefault(pSkill, 0);
        }

        for (Tag entry : this.data.getList("Skills", 10)) {
            CompoundTag skill = (CompoundTag) entry;
            if (skill.getString("Name").equals(pSkill.getName())) {
                return skill.getInt("Level");
            }
        }

        return 0;
    }

    @Override
    public void setLevel(ServerPlayer pServerPlayer, Skill pSkill, int pLevel) {
        this.putSkill(pSkill, Math.min(pLevel, pSkill.getProperties().getMaxLevel()));
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayer pServerPlayer, Skill pSkill, int pLevel) {
        this.setLevel(pServerPlayer, pSkill, getLevel(pServerPlayer, pSkill) + pLevel);
    }

    @Override
    public Specialization getSpecialization(Player pPlayer) {
        if (pPlayer.level().isClientSide) {
            return this.specialization;
        }

        String name = this.data.getCompound("Spec").getString("Name");
        return name.isEmpty() ? null : Specializations.getFrom(name);
    }

    @Override
    public void setSpecialization(ServerPlayer pServerPlayer, Specialization pSpecialization) {
        this.putSpecialization(pSpecialization);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public int getAvailableExperience(Player pPlayer) {
        if (pPlayer.level().isClientSide) {
            return this.availableExperience;
        }

        return this.data.getInt("AvailableXP");
    }

    @Override
    public void setAvailableExperience(ServerPlayer pServerPlayer, int pAmount) {
        this.putAvailableExperience(pAmount);
        this.sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void updateAvailableExperience(ServerPlayer pServerPlayer) {
        this.setAvailableExperience(pServerPlayer, PlayerUtil.getAvailableExperience(pServerPlayer));
    }

    @Override
    public void sendDataToPlayer(ServerPlayer pServerPlayer) {
        NetworkHandler.sendToPlayer(new SyncDataS2CPacket(this.data), pServerPlayer);
    }

    @Override
    public void receiveDataFromServer(CompoundTag pData) {
        for (Tag entry : pData.getList("Specs", 10)) {
            CompoundTag specialization = (CompoundTag) entry;
            this.specializations.put(Specializations.getFrom(specialization.getString("Name")), specialization.getBoolean("Unlocked"));
        }

        for (Tag entry : pData.getList("Skills", 10)) {
            CompoundTag skill = (CompoundTag) entry;
            this.skills.put(Skills.getFrom(skill.getString("Name")), skill.getInt("Level"));
        }

        String name = pData.getCompound("Spec").getString("Name");
        this.specialization = name.isEmpty() ? null : Specializations.getFrom(name);

        this.availableExperience = pData.getInt("AvailableXP");
    }

    @Override
    public CompoundTag getData() {
        return this.data;
    }

    @Override
    public void setData(CompoundTag pData) {
        this.data = pData;
    }

    private void putSpecialization(Specialization pSpecialization, boolean pUnlocked) {
        ListTag specs = this.data.getList("Specs", 10);

        CompoundTag spec = new CompoundTag();
        spec.putString("Name", pSpecialization.getName());
        spec.putBoolean("Unlocked", pUnlocked);

        if (specs.isEmpty()) {
            specs.add(spec);
            return;
        }

        for (Tag entry : specs) {
            CompoundTag nbt = (CompoundTag) entry;
            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                nbt.putBoolean("Unlocked", pUnlocked);
                return;
            }
        }

        specs.add(spec);
    }

    private void putSkill(Skill pSkill, int pLevel) {
        ListTag skills = this.data.getList("Skills", 10);

        CompoundTag skill = new CompoundTag();
        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);

        if (skills.isEmpty()) {
            skills.add(skill);
            return;
        }

        for (Tag entry : skills) {
            CompoundTag nbt = (CompoundTag) entry;
            if (nbt.getString("Name").equals(pSkill.getName())) {
                nbt.putInt("Level", Math.max(0, pLevel));
                return;
            }
        }

        skills.add(skill);
    }

    private void putSpecialization(Specialization pSpecialization) {
        CompoundTag spec = new CompoundTag();

        if (pSpecialization != null) {
            spec.putString("Name", pSpecialization.getName());
        }

        this.data.put("Spec", spec);
    }

    private void putAvailableExperience(int pAvailableXP) {
        this.data.putInt("AvailableXP", pAvailableXP);
    }
}
