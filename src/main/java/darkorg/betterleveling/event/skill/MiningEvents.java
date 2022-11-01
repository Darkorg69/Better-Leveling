package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static darkorg.betterleveling.registry.AttributeModifiers.SPRINT_SPEED_MODIFIER;
import static darkorg.betterleveling.registry.SkillRegistry.*;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MiningEvents {
    @SubscribeEvent
    public static void onStoneCutting(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (event.getState().getMaterial() == Material.ROCK) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, STONECUTTING)) {
                        int level = capability.getLevel(player, STONECUTTING);
                        float modifier = 1 + (level * 0.1F);
                        if (level > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onWoodcutting(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (event.getState().getMaterial() == Material.WOOD || event.getState().getMaterial() == Material.NETHER_WOOD) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, WOODCUTTING)) {
                        int level = capability.getLevel(player, WOODCUTTING);
                        float modifier = 1 + (level * 0.1F);
                        if (level > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onSoftLanding(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(player, SOFT_LANDING)) {
                    int level = capability.getLevel(player, SOFT_LANDING);
                    if (level > 0) {
                        float reduction = 1 - (level * 0.05F);
                        event.setDamageMultiplier(event.getDamageMultiplier() * reduction);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSprintSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SPRINT_SPEED)) {
                        int level = capability.getLevel(player, SPRINT_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.05F;
                            ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(SPRINT_SPEED_MODIFIER, SPRINT_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSprinting()) {
                                    if (attribute.getModifier(SPRINT_SPEED_MODIFIER) == null) {
                                        attribute.applyNonPersistentModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(SPRINT_SPEED_MODIFIER) != null) {
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
