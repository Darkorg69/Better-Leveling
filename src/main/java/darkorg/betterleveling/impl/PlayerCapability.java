package darkorg.betterleveling.impl;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.RegistryUtil;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCapability implements IPlayerCapability {
    private final Map<ISpecialization, Boolean> specMap;
    private final Map<ISkill, Integer> skillMap;
    private CompoundTag data;

    public PlayerCapability() {
        this.data = new CompoundTag();
        ListTag specs = new ListTag();
        ListTag skills = new ListTag();
        this.data.put("Specs", specs);
        this.data.put("Skills", skills);
        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> putUnlocked(pSpecialization, false));
        SkillRegistry.getSkillRegistry().forEach(pSkill -> putLevel(pSkill, 0));
        this.specMap = new HashMap<>();
        this.skillMap = new HashMap<>();
    }

    private void putUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        CompoundTag spec = new CompoundTag();
        spec.putString("Name", pSpecialization.getName());
        spec.putBoolean("Unlocked", pUnlocked);

        ListTag specs = this.data.getList("Specs", 10);

        if (specs.isEmpty()) {
            specs.add(spec);
            return;
        } else {
            for (Tag tag : specs) {
                CompoundTag nbt = (CompoundTag) tag;
                if (nbt.getString("Name").equals(pSpecialization.getName())) {
                    nbt.putBoolean("Unlocked", pUnlocked);
                    return;
                }
            }
        }
        specs.add(spec);
    }

    private void putLevel(ISkill pSkill, int pLevel) {
        CompoundTag skill = new CompoundTag();
        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);

        ListTag skills = this.data.getList("Skills", 10);

        if (skills.isEmpty()) {
            skills.add(skill);
            return;
        } else {
            for (Tag pTag : skills) {
                CompoundTag tag = (CompoundTag) pTag;
                if (tag.getString("Name").equals(pSkill.getName())) {
                    tag.putInt("Level", pLevel);
                    return;
                }
            }
        }
        skills.add(skill);
    }

    @Override
    public boolean canUnlock(Player pPlayer) {
        boolean canUnlock = pPlayer.experienceLevel >= ServerConfig.FIRST_SPEC_COST.get();
        if (!canUnlock) {
            pPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.CHOOSE_NO_XP).append(" ").append(String.valueOf(ServerConfig.FIRST_SPEC_COST.get())), true);
        }
        return canUnlock;
    }

    @Override
    public boolean hasUnlocked(Player pPlayer) {
        for (ISpecialization specialization : SpecRegistry.getSpecRegistry()) {
            boolean unlocked = getUnlocked(pPlayer, specialization);
            if (unlocked) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ISpecialization getFirstSpecialization(Player pPlayer) {
        return this.getSpecializations(pPlayer).get(0);
    }

    @Override
    public List<ISpecialization> getSpecializations(Player pPlayer) {
        List<ISpecialization> unlockedList = new ArrayList<>();
        SpecRegistry.getSpecRegistry().forEach(PlayerSpec -> {
            if (getUnlocked(pPlayer, PlayerSpec)) {
                unlockedList.add(PlayerSpec);
            }
        });
        return unlockedList;
    }

    @Override
    public boolean getUnlocked(Player pPlayer, ISpecialization pSpecialization) {
        if (pPlayer.level.isClientSide) {
            return this.specMap.getOrDefault(pSpecialization, false);
        }
        for (Tag tag : this.data.getList("Specs", 10)) {
            CompoundTag nbt = (CompoundTag) tag;
            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                return nbt.getBoolean("Unlocked");
            }
        }
        return false;
    }

    @Override
    public void setUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        putUnlocked(pSpecialization, pUnlocked);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        if (hasUnlocked(pServerPlayer)) {
            int unlockCost = pSpecialization.getLevelCost();
            if (unlockCost <= pServerPlayer.experienceLevel) {
                pServerPlayer.giveExperienceLevels(-unlockCost);
                setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
            } else {
                pServerPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.NOT_ENOUGH_XP), true);
            }
        } else {
            pServerPlayer.giveExperienceLevels(-ServerConfig.FIRST_SPEC_COST.get());
            setUnlocked(pServerPlayer, pSpecialization, pUnlocked);
        }
    }

    @Override
    public boolean hasUnlocked(Player pPlayer, ISkill pSkill) {
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
    public int getLevel(Player pPlayer, ISkill pSkill) {
        if (pPlayer.level.isClientSide) {
            return this.skillMap.getOrDefault(pSkill, 0);
        }
        for (Tag tag : this.data.getList("Skills", 10)) {
            CompoundTag nbt = (CompoundTag) tag;
            if (nbt.getString("Name").equals(pSkill.getName())) {
                return nbt.getInt("Level");
            }
        }
        return 0;
    }

    @Override
    public void setLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pLevel) {
        putLevel(pSkill, pLevel);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pAmount) {
        int currentLevel = getLevel(pServerPlayer, pSkill);
        if (pAmount > 0) {
            if (currentLevel >= pSkill.getMaxLevel()) {
                pServerPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.CANNOT_INCREASE), true);
            } else {
                int cost = SkillUtil.getCurrentCost(pSkill, currentLevel);
                if (cost <= pServerPlayer.totalExperience) {
                    pServerPlayer.giveExperiencePoints(-cost);
                    setLevel(pServerPlayer, pSkill, currentLevel + pAmount);
                } else {
                    pServerPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.NOT_ENOUGH_XP), true);
                }
            }
        } else {
            if (currentLevel == 0) {
                pServerPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.CANNOT_DECREASE), true);
            } else {
                pServerPlayer.giveExperiencePoints(Math.round(SkillUtil.getCurrentCost(pSkill, currentLevel - 1) * ServerConfig.XP_REFUND_FACTOR.get().floatValue()));
                setLevel((pServerPlayer), pSkill, currentLevel + pAmount);
            }
        }
    }

    @Override
    public CompoundTag getNBTData() {
        return this.data;
    }

    @Override
    public void setNBTData(CompoundTag pData) {
        this.data = pData;
    }

    @Override
    public void sendDataToPlayer(ServerPlayer pServerPlayer) {
        CompoundTag data = new CompoundTag();

        ListTag specs = new ListTag();
        ListTag skills = new ListTag();

        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("Name", pSpecialization.getName());
            tag.putBoolean("Unlocked", getUnlocked(pServerPlayer, pSpecialization));
            specs.add(tag);
        });
        data.put("Specs", specs);

        SkillRegistry.getSkillRegistry().forEach(pSkill -> {
            CompoundTag tag = new CompoundTag();
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
    public void receiveDataFromServer(CompoundTag pData) {
        if (pData != null) {
            if (pData.contains("Specs")) {
                for (Tag tag : pData.getList("Specs", 10)) {
                    CompoundTag nbt = (CompoundTag) tag;
                    this.specMap.put(RegistryUtil.getSpecFromName(nbt.getString("Name")), nbt.getBoolean("Unlocked"));
                }
            }
            if (pData.contains("Skills")) {
                for (Tag tag : pData.getList("Skills", 10)) {
                    CompoundTag nbt = (CompoundTag) tag;
                    this.skillMap.put(RegistryUtil.getSkillFromName(nbt.getString("Name")), nbt.getInt("Level"));
                }
            }
        }
    }
}
