package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.StackUtil;
import darkorg.betterleveling.util.StateUtil;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class MiningEvents {
    @SubscribeEvent
    public static void onMining(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.getUnlocked(serverPlayer, SpecRegistry.MINING)) {
                    serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(event.getExpToDrop() * new Random().nextDouble(ServerConfig.MINING_XP_BONUS.get()))));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onStoneCutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player != null && event.getState().getMaterial() == Material.STONE) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.STONECUTTING)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.STONECUTTING);
                    if (currentLevel > 0) {
                        event.setNewSpeed(event.getOriginalSpeed() * (float) SkillUtil.getIncreaseModifier(SkillRegistry.STONECUTTING, currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onProspecting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            BlockState state = event.getState();
            if (state.is(Tags.Blocks.ORES)) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(serverPlayer, SkillRegistry.PROSPECTING)) {
                        ServerLevel serverLevel = serverPlayer.getLevel();
                        int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.PROSPECTING);
                        if (currentLevel > 0) {
                            Random random = new Random();
                            if (random.nextFloat() <= SkillUtil.getCurrentChance(SkillRegistry.PROSPECTING, currentLevel)) {
                                BlockPos pos = event.getPos();
                                List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                                drops.stream().filter(StackUtil::isResource).forEach(stack -> {
                                    stack.setCount(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.PROSPECTING_POTENTIAL_LOOT_BOUND.get(), random));
                                    Block.popResource(serverLevel, pos, stack);
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onWoodcutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player != null) {
            Material material = event.getState().getMaterial();
            if (material == Material.WOOD || material == Material.NETHER_WOOD) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SkillRegistry.WOODCUTTING)) {
                        int currentLevel = capability.getLevel(player, SkillRegistry.WOODCUTTING);
                        if (currentLevel > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * (float) SkillUtil.getIncreaseModifier(SkillRegistry.WOODCUTTING, currentLevel));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onTreasureHunting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative() && StateUtil.isTreasureBlock(event.getState())) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, SkillRegistry.TREASURE_HUNTING)) {
                    int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.TREASURE_HUNTING);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() < SkillUtil.getCurrentChance(SkillRegistry.TREASURE_HUNTING, currentLevel)) {
                            BlockPos pos = event.getPos();
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            int bound = ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_UNCOMMON_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_COMMON_WEIGHT.get();
                            int roll = random.nextInt(bound);
                            if (roll < ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get()) {
                                TreasureUtil.spawnTreasure(serverLevel, pos, random, TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_RARE, serverLevel.random));
                            } else {
                                if (roll < ServerConfig.TREASURE_HUNTING_RARE_WEIGHT.get() + ServerConfig.TREASURE_HUNTING_UNCOMMON_WEIGHT.get()) {
                                    TreasureUtil.spawnTreasure(serverLevel, pos, random, TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_UNCOMMON, serverLevel.random));
                                } else {
                                    if (roll < bound) {
                                        TreasureUtil.spawnTreasure(serverLevel, pos, random, TreasureUtil.getRandomTreasure(ModTags.Items.TREASURES_COMMON, serverLevel.random));
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSoftLanding(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.SOFT_LANDING)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.SOFT_LANDING);
                    if (currentLevel > 0) {
                        event.setDamageMultiplier(event.getDamageMultiplier() * (float) SkillUtil.getDecreaseModifier(SkillRegistry.SOFT_LANDING, currentLevel));
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
                    if (capability.hasUnlocked(player, SkillRegistry.SPRINT_SPEED)) {
                        int currentLevel = capability.getLevel(player, SkillRegistry.SPRINT_SPEED);
                        if (currentLevel > 0) {
                            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER, SkillRegistry.SPRINT_SPEED.getName(), SkillUtil.getCurrentBonus(SkillRegistry.SPRINT_SPEED, currentLevel), AttributeModifier.Operation.MULTIPLY_BASE);
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
