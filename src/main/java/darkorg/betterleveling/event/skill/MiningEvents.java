package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.TreasureUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static darkorg.betterleveling.registry.SkillRegistry.*;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class MiningEvents {
    @SubscribeEvent
    public static void onStoneCutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        if (player != null && event.getState().getMaterial() == Material.STONE) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, STONECUTTING)) {
                    int currentLevel = capability.getLevel(player, STONECUTTING);
                    if (currentLevel > 0) {
                        event.setNewSpeed(event.getOriginalSpeed() * (float) SkillUtil.getIncreaseModifier(STONECUTTING, currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onProspecting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, PROSPECTING)) {
                    ServerLevel serverLevel = serverPlayer.getLevel();
                    int currentLevel = capability.getLevel(serverPlayer, PROSPECTING);
                    if (currentLevel > 0) {
                        if (serverLevel.random.nextFloat() <= SkillUtil.getCurrentChance(PROSPECTING, currentLevel)) {
                            BlockPos pos = event.getPos();
                            BlockState state = event.getState();
                            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                            drops.forEach(stack -> {
                                if (!stack.is(state.getBlock().asItem())) {
                                    if (stack.is(ModTags.Items.ORES)) {
                                        stack.setCount(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.PROSPECTING_POTENTIAL_LOOT_BOUND.get(), serverLevel.random));
                                        Block.popResource(serverLevel, pos, stack);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onWoodcutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        if (player != null) {
            Material material = event.getState().getMaterial();
            if (material == Material.WOOD || material == Material.NETHER_WOOD) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, WOODCUTTING)) {
                        int currentLevel = capability.getLevel(player, WOODCUTTING);
                        if (currentLevel > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * (float) SkillUtil.getIncreaseModifier(WOODCUTTING, currentLevel));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onTreasureHunting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            BlockState state = event.getState();
            if (state.is(ModTags.Blocks.TREASURE_BLOCKS)) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(serverPlayer, TREASURE_HUNTING)) {
                        int currentLevel = capability.getLevel(serverPlayer, TREASURE_HUNTING);
                        if (currentLevel > 0) {
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            if (serverPlayer.getLevel().random.nextFloat() <= SkillUtil.getCurrentChance(TREASURE_HUNTING, currentLevel)) {
                                BlockPos pos = event.getPos();
                                int bound = ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_UNCOMMON_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_COMMON_WEIGHT.get();
                                int roll = serverLevel.random.nextInt(1, bound);
                                if (roll <= ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get()) {
                                    TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandomTreasure(ModTags.Items.RARE_TREASURES, serverLevel.random));
                                } else {
                                    if (roll <= ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_UNCOMMON_WEIGHT.get()) {
                                        TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandomTreasure(ModTags.Items.UNCOMMON_TREASURES, serverLevel.random));
                                    } else {
                                        if (roll <= bound) {
                                            TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandomTreasure(ModTags.Items.COMMON_TREASURES, serverLevel.random));
                                        }
                                    }
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
        if (event.getEntity() instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SOFT_LANDING)) {
                    int currentLevel = capability.getLevel(player, SOFT_LANDING);
                    if (currentLevel > 0) {
                        event.setDamageMultiplier(event.getDamageMultiplier() * (float) SkillUtil.getDecreaseModifier(SOFT_LANDING, currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSprintSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SPRINT_SPEED)) {
                        int currentLevel = capability.getLevel(player, SPRINT_SPEED);
                        if (currentLevel > 0) {
                            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER, SPRINT_SPEED.getName(), SkillUtil.getCurrentBonus(SPRINT_SPEED, currentLevel), AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSprinting()) {
                                    if (attribute.getModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER) == null) {
                                        attribute.addTransientModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER) != null) {
                                        attribute.removeModifier(attributeModifier);
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
