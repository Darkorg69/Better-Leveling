package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CombatEvents {
    @SubscribeEvent
    public static void onCombat(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.getUnlocked(serverPlayer, SpecRegistry.COMBAT)) {
                    serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(event.getOriginalExperience() * new Random().nextDouble(ServerConfig.COMBAT_XP_BONUS.get()))));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onStrength(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, SkillRegistry.STRENGTH)) {
                    int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.STRENGTH);
                    if (currentLevel > 0) {
                        event.setAmount(event.getAmount() * (float) SkillUtil.getIncreaseModifier(SkillRegistry.STRENGTH, currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onCriticalStrike(CriticalHitEvent event) {
        if (ServerConfig.VANILLA_CRIT_DISABLED.get() && event.isVanillaCritical()) {
            event.setResult(Event.Result.DENY);
        }
        Player player = event.getEntity();
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
            if (capability.hasUnlocked(player, SkillRegistry.CRITICAL_STRIKE)) {
                int currentLevel = capability.getLevel(player, SkillRegistry.CRITICAL_STRIKE);
                if (currentLevel > 0) {
                    if (player.getRandom().nextDouble() <= SkillUtil.getCurrentChance(SkillRegistry.CRITICAL_STRIKE, currentLevel)) {
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onQuickDraw(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof Player player && event.getItem().getItem() instanceof BowItem) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.QUICK_DRAW)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.QUICK_DRAW);
                    if (currentLevel > 0) {
                        event.setDuration(event.getDuration() - Math.toIntExact(Math.round(20 * SkillUtil.getCurrentBonus(SkillRegistry.QUICK_DRAW, currentLevel))));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onArrowSpeed(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.ARROW_SPEED)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.ARROW_SPEED);
                    if (currentLevel > 0) {
                        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(SkillUtil.getIncreaseModifier(SkillRegistry.ARROW_SPEED, currentLevel)));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onIronSkin(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource() != DamageSource.FALL) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.IRON_SKIN)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.IRON_SKIN);
                    if (currentLevel > 0) {
                        event.setAmount(event.getAmount() * (float) SkillUtil.getDecreaseModifier(SkillRegistry.IRON_SKIN, currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSneakSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SkillRegistry.SNEAK_SPEED)) {
                        int currentLevel = capability.getLevel(player, SkillRegistry.SNEAK_SPEED);
                        if (currentLevel > 0) {
                            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER, SkillRegistry.SNEAK_SPEED.getName(), SkillUtil.getCurrentBonus(SkillRegistry.SNEAK_SPEED, currentLevel), AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isCrouching()) {
                                    if (attribute.getModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER) == null) {
                                        attribute.addTransientModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER) != null) {
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
