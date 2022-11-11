package darkorg.betterleveling.impl;

import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
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
        SpecRegistry.getSpecRegistry().forEach(pSpecialization -> setUnlocked(pSpecialization, false));
        SkillRegistry.getSkillRegistry().forEach(pSkill -> setLevel(pSkill, 0));
        this.specMap = new HashMap<>();
        this.skillMap = new HashMap<>();
    }

    @Override
    public boolean hasUnlocked(Player pPlayer) {
        for (ISpecialization playerSpecialization : SpecRegistry.getSpecRegistry()) {
            boolean unlocked = getUnlocked(pPlayer, playerSpecialization);
            if (unlocked) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getUnlocked(Player pPlayer, ISpecialization pSpecialization) {
        if (pPlayer.level.isClientSide) return this.specMap.getOrDefault(pSpecialization, false);
        for (Tag tag : this.data.getList("Specs", 10)) {
            CompoundTag nbt = (CompoundTag) tag;
            if (nbt.getString("Name").equals(pSpecialization.getName())) {
                return nbt.getBoolean("Unlocked");
            }
        }
        return false;
    }

    @Override
    public void addUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
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
    public void setUnlocked(ServerPlayer pServerPlayer, ISpecialization pSpecialization, boolean pUnlocked) {
        setUnlocked(pSpecialization, pUnlocked);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public boolean canUnlock(Player pPlayer) {
        boolean canUnlock = pPlayer.experienceLevel >= ServerConfig.FIRST_SPEC_COST.get();
        if (!canUnlock) {
            pPlayer.displayClientMessage(new TranslatableComponent("").append(ModComponents.CHOOSE_NO_XP).append(" ").append(String.valueOf(ServerConfig.FIRST_SPEC_COST.get())), true);
        }
        return canUnlock;
    }

    @Override
    public ISpecialization getFirstUnlocked(Player pPlayer) {
        return this.getAllUnlocked(pPlayer).get(0);
    }

    @Override
    public List<ISpecialization> getAllUnlocked(Player pPlayer) {
        List<ISpecialization> unlockedList = new ArrayList<>();
        SpecRegistry.getSpecRegistry().forEach(PlayerSpec -> {
            if (getUnlocked(pPlayer, PlayerSpec)) {
                unlockedList.add(PlayerSpec);
            }
        });
        return unlockedList;
    }

    @Override
    public int getLevel(Player pPlayer, ISkill pSkill) {
        if (pPlayer.level.isClientSide) return this.skillMap.getOrDefault(pSkill, 0);
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
        setLevel(pSkill, pLevel);
        sendDataToPlayer(pServerPlayer);
    }

    @Override
    public void addLevel(ServerPlayer pServerPlayer, ISkill pSkill, int pLevel) {
        int currentLevel = getLevel(pServerPlayer, pSkill);
        if (pLevel > 0) {
            if (currentLevel >= pSkill.getMaxLevel()) {
                pServerPlayer.displayClientMessage(ModComponents.CANNOT_INCREASE, true);
            } else {
                int levelCost = pSkill.getIncreaseCost(currentLevel);
                if (levelCost <= pServerPlayer.experienceLevel) {
                    pServerPlayer.giveExperienceLevels(-levelCost);
                    setLevel(pServerPlayer, pSkill, currentLevel + pLevel);
                } else {
                    pServerPlayer.displayClientMessage(ModComponents.NOT_ENOUGH_XP, true);
                }
            }
        } else {
            if (currentLevel <= pSkill.getMinLevel()) {
                pServerPlayer.displayClientMessage(ModComponents.CANNOT_DECREASE, true);
            } else {
                pServerPlayer.giveExperienceLevels(Math.round(pSkill.getIncreaseCost(currentLevel - 1) / 2.0F));
                setLevel((pServerPlayer), pSkill, currentLevel + pLevel);
            }
        }
    }

    @Override
    public boolean isUnlocked(Player pPlayer, ISkill pSkill) {
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

        SpecRegistry.getSpecRegistry().forEach(PlayerSpec -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("Name", PlayerSpec.getName());
            tag.putBoolean("Unlocked", getUnlocked(pServerPlayer, PlayerSpec));
            specs.add(tag);
        });
        data.put("Specs", specs);

        SkillRegistry.getSkillRegistry().forEach(PlayerSkill -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("Name", PlayerSkill.getName());
            tag.putInt("Level", getLevel(pServerPlayer, PlayerSkill));
            skills.add(tag);
        });
        data.put("Skills", skills);

        NetworkHandler.sendToPlayer(new SyncDataS2CPacket(data), pServerPlayer);
    }

    @Override
    public void resetPlayer(ServerPlayer pServerPlayer) {

    }

    @Override
    public void receiveDataFromServer(CompoundTag pData) {
        if (pData != null) {
            if (pData.contains("Specs")) {
                for (Tag tag : pData.getList("Specs", 10)) {
                    CompoundTag nbt = (CompoundTag) tag;
                    this.specMap.put(CapabilityUtil.getSpecFromName(nbt.getString("Name")), nbt.getBoolean("Unlocked"));
                }
            }
            if (pData.contains("Skills")) {
                for (Tag tag : pData.getList("Skills", 10)) {
                    CompoundTag nbt = (CompoundTag) tag;
                    this.skillMap.put(CapabilityUtil.getSkillFromName(nbt.getString("Name")), nbt.getInt("Level"));
                }
            }
        }
    }

    private void putUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", pSpecialization.getName());
        tag.putBoolean("Unlocked", pUnlocked);
        this.data.getList("Specs", 10).add(tag);
    }

    private void setUnlocked(ISpecialization pSpecialization, boolean pUnlocked) {
        if (this.data.getList("Specs", 10).isEmpty()) {
            putUnlocked(pSpecialization, pUnlocked);
            return;
        } else {
            for (Tag tag : this.data.getList("Specs", 10)) {
                CompoundTag nbt = (CompoundTag) tag;
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
            for (Tag tag : this.data.getList("Skills", 10)) {
                CompoundTag nbt = (CompoundTag) tag;
                if (nbt.getString("Name").equals(pSkill.getName())) {
                    nbt.putInt("Level", pLevel);
                    return;
                }
            }
        }
        putLevel(pSkill, pLevel);
    }

    private void putLevel(ISkill pSkill, int pLevel) {
        CompoundTag skill = new CompoundTag();
        skill.putString("Name", pSkill.getName());
        skill.putInt("Level", pLevel);
        this.data.getList("Skills", 10).add(skill);
    }
}
