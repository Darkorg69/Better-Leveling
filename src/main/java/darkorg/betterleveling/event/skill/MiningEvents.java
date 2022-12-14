package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
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

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class MiningEvents {
    @SubscribeEvent
    public static void onStoneCutting(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        if (player != null) {
            if (event.getState().getMaterial() == Material.STONE) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.STONECUTTING)) {
                        int level = capability.getLevel(player, SkillRegistry.STONECUTTING);
                        if (level > 0) {
                            float modifier = 1.0F + (level * 0.1F);
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onProspecting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.PROSPECTING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.PROSPECTING);
                    if (level > 0) {
                        BlockState state = event.getState();
                        if (state.is(ModTags.Blocks.ORES)) {
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            BlockPos pos = event.getPos();
                            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null);
                            drops.forEach(stack -> {
                                if (stack.is(ModTags.Items.ORES)) {
                                    stack.setCount(Math.round(stack.getCount() * serverLevel.random.nextFloat(0, level * 0.5F)));
                                    Block.popResource(serverLevel, pos, stack);
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
                    if (capability.isUnlocked(player, SkillRegistry.WOODCUTTING)) {
                        int level = capability.getLevel(player, SkillRegistry.WOODCUTTING);
                        float modifier = 1.0F + (level * 0.1F);
                        if (level > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onTreasureHunting(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.TREASURE_HUNTING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.TREASURE_HUNTING);
                    if (level > 0) {
                        if (event.getState().is(ModTags.Blocks.TREASURE_BLOCKS)) {
                            BlockPos pos = event.getPos();
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            float roll = serverLevel.random.nextFloat();
                            if (roll <= level * 0.001F) {
                                TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandom(ModTags.Items.TREASURE_RARE, serverLevel.random));
                            } else if (roll <= level * 0.002F) {
                                TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandom(ModTags.Items.TREASURE_UNCOMMON, serverLevel.random));
                            } else if (roll <= level * 0.004F) {
                                TreasureUtil.spawnTreasure(serverLevel, pos, serverLevel.random, TreasureUtil.getRandom(ModTags.Items.TREASURE_COMMON, serverLevel.random));
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
                if (capability.isUnlocked(player, SkillRegistry.SOFT_LANDING)) {
                    int level = capability.getLevel(player, SkillRegistry.SOFT_LANDING);
                    if (level > 0) {
                        float reduction = 1.0F - (level * 0.05F);
                        event.setDamageMultiplier(event.getDamageMultiplier() * reduction);
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
                    if (capability.isUnlocked(player, SkillRegistry.SPRINT_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SPRINT_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.05F;
                            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER, SkillRegistry.SPRINT_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
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
