package darkorg.betterleveling.impl;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.RegistryUtil;
import darkorg.betterleveling.util.SkillUtil;
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

public class PlayerCapability implements IPlayerCapability {
    private final Map<ISpecialization, Boolean> specMap;
    private final Map<ISkill, Integer> skillMap;
    private CompoundNBT data;

    public PlayerCapability() {
        this.data = new CompoundNBT();
        ListNBT specs = new ListNBT();
        ListNBT skills = new ListNBT();
        this.data.put("Specs", specs);
        this.data.put("Skills", skills);
        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> putUnlocked(pSpecialization, false));
        SkillRegistry.getSkillRegistry().forEach(pSkill -> putLevel(pSkill, 0));
        this.specMap = new HashMap<>();
        this.skillMap = new HashMap<>();
    }

    private void putUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        CompoundNBT spec = new CompoundNBT();
        spec.putString("Name", pSpecialization.getName());
        spec.putBoolean("Unlocked", pUnlocked);

        ListNBT specs = this.data.getList("Specs", 10);

        if (specs.isEmpty()) {
            specs.add(spec);
            return;
        } else {
            for (INBT tag : specs) {
                CompoundNBT nbt = (CompoundNBT) tag;
                if (nbt.getString("Name").equals(pSpecialization.getName())) {
                    nbt.putBoolean("Unlocked", pUnlocked);
                    return;
                }
            }
        }
        specs.add(spec);
    }

    private void putLevel(ISkill pSkill, int pLevel) {
        CompoundNBT skill = new CompoundNBT();
        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);

        ListNBT skills = this.data.getList("Skills", 10);

        if (skills.isEmpty()) {
            skills.add(skill);
            return;
        } else {
            for (INBT pTag : skills) {
                CompoundNBT tag = (CompoundNBT) pTag;
                if (tag.getString("Name").equals(pSkill.getName())) {
                    tag.putInt("Level", pLevel);
                    return;
                }
            }
        }
        skills.add(skill);
    }

    @Override
    public boolean canUnlock(PlayerEntity pPlayer) {
        boolean canUnlock = pPlayer.experienceLevel >= ServerConfig.FIRST_SPEC_COST.get();
        if (!canUnlock) {
            pPlayer.displayClientMessage(new TranslationTextComponent("").append(ModComponents.CHOOSE_NO_XP).append(" ").append(String.valueOf(ServerConfig.FIRST_SPEC_COST.get())), true);
        }
        return canUnlock;
    }

    @Override
    public boolean hasUnlocked(PlayerEntity pPlayer) {
        for (ISpecialization specialization : SpecRegistry.getSpecRegistry()) {
            boolean unlocked = getUnlocked(pPlayer, specialization);
            if (unlocked) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ISpecialization getFirstSpecialization(PlayerEntity pPlayer) {
        return this.getSpecializations(pPlayer).get(0);
    }

    @Override
    public List<ISpecialization> getSpecializations(PlayerEntity pPlayer) {
        List<ISpecialization> unlockedList = new ArrayList<>();
        SpecRegistry.getSpecRegistry().forEach(PlayerSpec -> {
            if (getUnlocked(pPlayer, PlayerSpec)) {
                unlockedList.add(PlayerSpec);
            }
        });
        return unlockedList;
    }

    @Override
    public boolean getUnlocked(PlayerEntity pPlayer, ISpecialization pSpecialization) {
        if (pPlayer.level.isClientSide) {
            return this.specMap.getOrDefault(pSpecialization, false);
        }
        for (INBT tag : this.data.getList("Specs", 10)) {
            CompoundNBT nbt = (CompoundNBT) tag;
            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                return nbt.getBoolean("Unlocked");
            }
        }
        return false;
    }

    @Override
    public void setUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        putUnlocked(pSpecialization, pUnlocked);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addUnlocked(ServerPlayerEntity pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        if (hasUnlocked(pServerPlayer)) {
            int unlockCost = pSpecialization.getLevelCost();
            if (unlockCost <= pServerPlayer.experienceLevel) {
                pServerPlayer.giveExperienceLevels(-unlockCost);
                setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
            } else {
                pServerPlayer.displayClientMessage(ModComponents.NOT_ENOUGH_XP, true);
            }
        } else {
            pServerPlayer.giveExperienceLevels(-ServerConfig.FIRST_SPEC_COST.get());
            setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
        }
    }

    @Override
    public boolean hasUnlocked(PlayerEntity pPlayer, ISkill pSkill) {
        if (getUnlocked(pPlayer, pSkill.getParentSpec())) {
            List<Boolean> logicalResult = new ArrayList<>();
            pSkill.getPrerequisites().forEach((prerequisite, level) -> {
                getLevel(pPlayer, prerequisite);
                logicalResult.add(getLevel(pPlayer, prerequisite) >= level);
            });
            return !logicalResult.contains(false);
        } else {
            return false;
        }
    }

    @Override
    public int getLevel(PlayerEntity pPlayer, ISkill pSkill) {
        if (pPlayer.level.isClientSide) {
            return this.skillMap.getOrDefault(pSkill, 0);
        }
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
        putLevel(pSkill, pLevel);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayerEntity pServerPlayer, ISkill pSkill, int pAmount) {
        int currentLevel = getLevel(pServerPlayer, pSkill);
        if (pAmount > 0) {
            if (currentLevel >= pSkill.getMaxLevel()) {
                pServerPlayer.displayClientMessage(ModComponents.CANNOT_INCREASE, true);
            } else {
                int cost = SkillUtil.getCurrentCost(pSkill, currentLevel);
                if (cost <= pServerPlayer.totalExperience) {
                    pServerPlayer.giveExperiencePoints(-cost);
                    setLevel(pServerPlayer, pSkill, currentLevel + pAmount);
                } else {
                    pServerPlayer.displayClientMessage(ModComponents.NOT_ENOUGH_XP, true);
                }
            }
        } else {
            if (currentLevel == 0) {
                pServerPlayer.displayClientMessage(ModComponents.CANNOT_DECREASE, true);
            } else {
                pServerPlayer.giveExperiencePoints(Math.round(SkillUtil.getCurrentCost(pSkill, currentLevel - 1) * ServerConfig.XP_REFUND_FACTOR.get().floatValue()));
                setLevel((pServerPlayer), pSkill, currentLevel + pAmount);
            }
        }
    }

    @Override
    public CompoundNBT getNBTData() {
        return this.data;
    }

    @Override
    public void setNBTData(CompoundNBT pData) {
        this.data = pData;
    }

    @Override
    public void sendDataToPlayer(ServerPlayerEntity pServerPlayer) {
        CompoundNBT data = new CompoundNBT();

        ListNBT specs = new ListNBT();
        ListNBT skills = new ListNBT();

        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("Name", pSpecialization.getName());
            tag.putBoolean("Unlocked", getUnlocked(pServerPlayer, pSpecialization));
            specs.add(tag);
        });
        data.put("Specs", specs);

        SkillRegistry.getSkillRegistry().forEach(pSkill -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("Name", pSkill.getName());
            int maxLevel = pSkill.getMaxLevel();
            int currentLevel = getLevel(pServerPlayer, pSkill);
            if (currentLevel > maxLevel) {
                BetterLeveling.LOGGER.warn("Skill: " + pSkill.getName() + ", current level (" + currentLevel + ") exceeds the maximum level (" + maxLevel + ") at player " + pServerPlayer.getScoreboardName());
                putLevel(pSkill, maxLevel);
            }
            tag.putInt("Level", getLevel(pServerPlayer, pSkill));
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
                    this.specMap.put(RegistryUtil.getSpecFromName(nbt.getString("Name")), nbt.getBoolean("Unlocked"));
                }
            }
            if (pData.contains("Skills")) {
                for (INBT tag : pData.getList("Skills", 10)) {
                    CompoundNBT nbt = (CompoundNBT) tag;
                    this.skillMap.put(RegistryUtil.getSkillFromName(nbt.getString("Name")), nbt.getInt("Level"));
                }
            }
        }
    }
}
