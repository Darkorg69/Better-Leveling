package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static darkorg.betterleveling.network.chat.ModTextComponents.*;

public class PlayerCapability implements IPlayerCapability {
    private CompoundNBT data;

    private final Map<ISpecialization, Boolean> specMap;
    private final Map<ISkill, Integer> skillMap;

    public PlayerCapability() {
        this.data = new CompoundNBT();

        ListNBT specs = new ListNBT();
        ListNBT skills = new ListNBT();

        this.data.put("Specs", specs);
        this.data.put("Skills", skills);

        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> setUnlocked(pSpecialization, false));
        SkillRegistry.getSkillRegistry().forEach(pSkill -> setLevel(pSkill, 0));

        this.specMap = new HashMap<>();
        this.skillMap = new HashMap<>();
    }

    @Override
    public boolean hasUnlocked(PlayerEntity pPlayer) {
        for (ISpecialization playerSpecialization : SpecRegistry.getSpecRegistry()) {
            boolean unlocked = getUnlocked(pPlayer, playerSpecialization);

            if (unlocked) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getUnlocked(PlayerEntity pPlayer, ISpecialization pSpecialization) {
        if (pPlayer.world.isRemote) return this.specMap.getOrDefault(pSpecialization, false);

        for (INBT tag : this.data.getList("Specs", 10)) {
            CompoundNBT nbt = (CompoundNBT) tag;

            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                return nbt.getBoolean("Unlocked");
            }
        }
        return false;
    }

    @Override
    public void addUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        if (hasUnlocked(pServerPlayer)) {
            int unlockCost = pSpecialization.getLevelCost();

            if (unlockCost <= pServerPlayer.experienceLevel) {
                pServerPlayer.addExperienceLevel(-unlockCost);
                setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
            } else {
                pServerPlayer.sendStatusMessage(NOT_ENOUGH_XP, true);
            }
        } else {
            pServerPlayer.addExperienceLevel(-ServerConfig.firstSpecLevelCost.get());
            setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
        }
    }

    @Override
    public void setUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        setUnlocked(pSpecialization, true);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public boolean canUnlock(PlayerEntity pPlayer) {
        boolean canUnlock = pPlayer.experienceLevel >= ServerConfig.firstSpecLevelCost.get();

        if (!canUnlock) {
            pPlayer.sendStatusMessage(new TranslationTextComponent("").appendSibling(CHOOSE_NO_XP).appendString(" ").appendString(String.valueOf(ServerConfig.firstSpecLevelCost.get())), true);
        }
        return canUnlock;
    }

    @Override
    public ISpecialization getFirstUnlocked(PlayerEntity pPlayer) {
        return this.getAllUnlocked(pPlayer).get(0);
    }

    @Override
    public List<ISpecialization> getAllUnlocked(PlayerEntity pPlayer) {
        List<ISpecialization> unlockedList = new ArrayList<>();

        SpecRegistry.getSpecRegistry().forEach(PlayerEntitySpec -> {
            if (getUnlocked(pPlayer, PlayerEntitySpec)) {
                unlockedList.add(PlayerEntitySpec);
            }
        });

        return unlockedList;
    }

    @Override
    public int getLevel(PlayerEntity pPlayer, ISkill pSkill) {
        if (pPlayer.world.isRemote) return this.skillMap.getOrDefault(pSkill, 0);

        for (INBT tag : this.data.getList("Skills", 10)) {
            CompoundNBT nbt = (CompoundNBT) tag;

            if (nbt.getString("Name").equals(pSkill.getName())) {
                return nbt.getInt("Level");
            }
        }
        return 0;
    }

    @Override
    public void setLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pLevel) {
        setLevel(pSkill, pLevel);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pLevel) {
        int currentLevel = getLevel(pServerPlayer, pSkill);

        if (pLevel > 0) {
            if (currentLevel >= pSkill.getMaxLevel()) {
                pServerPlayer.sendStatusMessage(CANNOT_INCREASE, true);
            } else {
                int levelCost = pSkill.getLevelCost(currentLevel);
                if (levelCost <= pServerPlayer.experienceLevel) {
                    pServerPlayer.addExperienceLevel(-levelCost);
                    setLevel(pServerPlayer, pSkill, currentLevel + pLevel);
                } else {
                    pServerPlayer.sendStatusMessage(NOT_ENOUGH_XP, true);
                }
            }
        } else {
            if (currentLevel <= pSkill.getMinLevel()) {
                pServerPlayer.sendStatusMessage(CANNOT_DECREASE, true);
            } else {
                pServerPlayer.addExperienceLevel(currentLevel);
                setLevel((pServerPlayer), pSkill, currentLevel + pLevel);
            }
        }
    }

    @Override
    public boolean isUnlocked(PlayerEntity pPlayer, ISkill pSkill) {
        if (getUnlocked(pPlayer, pSkill.getParentSpec())) {
            Map<ISkill, Integer> prerequisites = pSkill.getPrerequisites();

            List<Boolean> logicalResult = new ArrayList<>();

            prerequisites.forEach((prerequisite, level) -> {
                getLevel(pPlayer, prerequisite);

                logicalResult.add(getLevel(pPlayer, prerequisite) >= level);
            });

            return !logicalResult.contains(false);
        } else {
            return false;
        }
    }

    @Override
    public CompoundNBT getData() {
        return this.data;
    }

    @Override
    public void setData(CompoundNBT pData) {
        this.data = pData;
    }

    @Override
    public void sendDataToPlayer(ServerPlayerEntity pServerPlayer) {
        CompoundNBT data = new CompoundNBT();

        ListNBT specs = new ListNBT();
        ListNBT skills = new ListNBT();

        SpecRegistry.getSpecRegistry().forEach(PlayerEntitySpec -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("Name", PlayerEntitySpec.getName());
            tag.putBoolean("Unlocked", getUnlocked(pServerPlayer, PlayerEntitySpec));
            specs.add(tag);
        });
        data.put("Specs", specs);

        SkillRegistry.getSkillRegistry().forEach(PlayerEntitySkill -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("Name", PlayerEntitySkill.getName());
            tag.putInt("Level", getLevel(pServerPlayer, PlayerEntitySkill));
            skills.add(tag);
        });
        data.put("Skills", skills);

        NetworkHandler.sendToPlayer(new SyncDataS2CPacket(data), pServerPlayer);
    }

    @Override
    public void receiveDataFromServer(CompoundNBT pData) {
        if (pData != null) {
            if (pData.contains("Specs")) {
                for (INBT tag : pData.getList("Specs", 10)) {
                    CompoundNBT nbt = (CompoundNBT) tag;
                    this.specMap.put(CapabilityUtil.getSpecFromName(nbt.getString("Name")), nbt.getBoolean("Unlocked"));
                }
            }
            if (pData.contains("Skills")) {
                for (INBT tag : pData.getList("Skills", 10)) {
                    CompoundNBT nbt = (CompoundNBT) tag;
                    this.skillMap.put(CapabilityUtil.getSkillFromName(nbt.getString("Name")), nbt.getInt("Level"));
                }
            }
        }
    }

    private void putUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        CompoundNBT tag = new CompoundNBT();

        tag.putString("Name", pSpecialization.getName());
        tag.putBoolean("Unlocked", pUnlocked);

        this.data.getList("Specs", 10).add(tag);
    }

    private void setUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        if (this.data.getList("Specs", 10).isEmpty()) {
            putUnlocked(pSpecialization, pUnlocked);
            return;
        } else {
            for (INBT tag : this.data.getList("Specs", 10)) {
                CompoundNBT nbt = (CompoundNBT) tag;

                if (nbt.getString("Name").equals(pSpecialization.getName())) {
                    nbt.putBoolean("Unlocked", pUnlocked);
                    return;
                }
            }
        }
        putUnlocked(pSpecialization, pUnlocked);
    }

    private void setLevel(ISkill pSkill, int pLevel) {
        if (this.data.getList("Skills", 10).isEmpty()) {
            putLevel(pSkill, pLevel);
            return;
        } else {
            for (INBT tag : this.data.getList("Skills", 10)) {
                CompoundNBT nbt = (CompoundNBT) tag;
                if (nbt.getString("Name").equals(pSkill.getName())) {
                    nbt.putInt("Level", pLevel);
                    return;
                }
            }
        }
        putLevel(pSkill, pLevel);
    }

    private void putLevel(ISkill pSkill, int pLevel) {
        CompoundNBT skill = new CompoundNBT();

        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);

        this.data.getList("Skills", 10).add(skill);
    }
}
