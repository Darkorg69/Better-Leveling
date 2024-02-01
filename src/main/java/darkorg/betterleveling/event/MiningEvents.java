package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.TreasureUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class MiningEvents {
    @SubscribeEvent
    public static void onMining(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.getUnlocked(serverPlayer, Specializations.MINING.get())) {
                        double currentBonus = new Random().nextDouble(0.5D, ModConfig.SPECIALIZATIONS.miningBonus.get());
                        serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(event.getExpToDrop() * currentBonus)));
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onStonecutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player != null && event.getState().getMapColor(player.level(), event.getPosition().orElseThrow()) == MapColor.STONE) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.STONECUTTING.get();
                if (SkillUtil.hasUnlocked(capability, player, skill)) {
                    int currentLevel = capability.getLevel(player, skill);
                    if (currentLevel > 0) {
                        double currentBonus = 1.0D + skill.getCurrentBonus(currentLevel);
                        event.setNewSpeed(event.getOriginalSpeed() * (float) currentBonus);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onProspecting(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                BlockState state = event.getState();
                if (state.is(Tags.Blocks.ORES)) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        Skill skill = Skills.PROSPECTING.get();
                        if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                            ServerLevel serverLevel = serverPlayer.serverLevel();
                            int currentLevel = capability.getLevel(serverPlayer, skill);
                            if (currentLevel > 0) {
                                Random random = new Random();
                                if (random.nextFloat() <= skill.getCurrentBonus(currentLevel)) {
                                    BlockPos pos = event.getPos();
                                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                                    for (ItemStack stack : drops) {
                                        if (stack.getItem() != state.getBlock().asItem()) {
                                            int originalCount = stack.getCount();
                                            int potentialLoot = TreasureUtil.getPotentialLoot(originalCount, ModConfig.SKILLS.prospectingPotentialLootBound.get());
                                            stack.setCount(potentialLoot);
                                            Block.popResource(serverLevel, pos, stack);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWoodcutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player != null) {
            MapColor mapColor = event.getState().getMapColor(player.level(), event.getPosition().orElseThrow());
            if (mapColor == MapColor.WOOD) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.WOODCUTTING.get();
                    if (SkillUtil.hasUnlocked(capability, player, skill)) {
                        int currentLevel = capability.getLevel(player, skill);
                        if (currentLevel > 0) {
                            double currentBonus = 1.0D + skill.getCurrentBonus(currentLevel);
                            event.setNewSpeed(event.getOriginalSpeed() * (float) currentBonus);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onTreasureHunting(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            BlockState state = event.getState();
            if (!serverPlayer.isCreative() && state.is(ModTags.Blocks.TREASURE_BLOCKS)) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.TREASURE_HUNTING.get();
                    if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                        int currentLevel = capability.getLevel(serverPlayer, skill);
                        if (currentLevel > 0) {
                            Random random = new Random();
                            if (random.nextDouble() < skill.getCurrentBonus(currentLevel)) {
                                BlockPos pos = event.getPos();
                                ServerLevel serverLevel = serverPlayer.serverLevel();
                                int rare = ModConfig.SKILLS.treasureHuntingRareWeight.get();
                                int uncommon = ModConfig.SKILLS.treasureHuntingUncommonWeight.get();
                                int common = ModConfig.SKILLS.treasureHuntingCommonWeight.get();
                                int bound = rare + uncommon + common;
                                int roll = random.nextInt(bound);
                                if (roll < rare) {
                                    ItemStack treasure = TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_RARE, serverLevel.random);
                                    TreasureUtil.spawnTreasure(serverLevel, pos, random, treasure);
                                    return;
                                }
                                if (roll < rare + uncommon) {
                                    ItemStack treasure = TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_UNCOMMON, serverLevel.random);
                                    TreasureUtil.spawnTreasure(serverLevel, pos, random, treasure);
                                    return;
                                }
                                if (roll < bound) {
                                    ItemStack treasure = TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_COMMON, serverLevel.random);
                                    TreasureUtil.spawnTreasure(serverLevel, pos, random, treasure);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onSoftLanding(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.SOFT_LANDING.get();
                if (SkillUtil.hasUnlocked(capability, player, skill)) {
                    int currentLevel = capability.getLevel(player, skill);
                    if (currentLevel > 0) {
                        double currentBonus = 1.0D - skill.getCurrentBonus(currentLevel);
                        event.setDamageMultiplier(event.getDamageMultiplier() * (float) currentBonus);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSprintSpeed(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && event.player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.SPRINT_SPEED.get();
                if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                    int currentLevel = capability.getLevel(serverPlayer, skill);
                    if (currentLevel > 0) {
                        AttributeInstance attribute = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
                        if (attribute != null) {
                            UUID uuid = AttributeModifiers.SPRINT_SPEED_MODIFIER;
                            AttributeModifier modifier = attribute.getModifier(uuid);
                            if (serverPlayer.isSprinting()) {
                                if (modifier == null) {
                                    attribute.addPermanentModifier(new AttributeModifier(uuid, skill.getName(), skill.getCurrentBonus(currentLevel), AttributeModifier.Operation.MULTIPLY_BASE));
                                }
                                return;
                            }
                            if (modifier != null) {
                                attribute.removeModifier(uuid);
                            }
                        }
                    }
                }
            });
        }
    }
}
