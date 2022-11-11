package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CombatEvents {
    @SubscribeEvent
    public static void onStrength(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(player, SkillRegistry.STRENGTH)) {
                    int level = capability.getLevel(player, SkillRegistry.STRENGTH);
                    if (level > 0) {
                        float modifier = 1.0F + (level * 0.075F);
                        event.setAmount(event.getAmount() * modifier);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onCriticalStrike(CriticalHitEvent event) {
        if (event.isVanillaCritical()) event.setResult(Event.Result.DENY);
        Player player = event.getPlayer();
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
            if (capability.isUnlocked(player, SkillRegistry.CRITICAL_STRIKE)) {
                int level = capability.getLevel(player, SkillRegistry.CRITICAL_STRIKE);
                if (level > 0) {
                    Random random = new Random();
                    float chance = level * 0.03F;
                    if (random.nextFloat() < chance) {
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
                if (capability.isUnlocked(player, SkillRegistry.QUICK_DRAW)) {
                    int level = capability.getLevel(player, SkillRegistry.QUICK_DRAW);
                    if (level > 0) {
                        event.setDuration(event.getDuration() - level);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onArrowSpeed(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof Player player) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(player, SkillRegistry.ARROW_SPEED)) {
                    int level = capability.getLevel(player, SkillRegistry.ARROW_SPEED);
                    if (level > 0) {
                        float modifier = 1.0F + (level * 0.03F);
                        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(modifier));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onIronSkin(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player player && event.getSource() != DamageSource.FALL) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(player, SkillRegistry.IRON_SKIN)) {
                    int level = capability.getLevel(player, SkillRegistry.IRON_SKIN);
                    if (level > 0) {
                        float modifier = 1.0F - (level * 0.03F);
                        event.setAmount(event.getAmount() * modifier);
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
                    if (capability.isUnlocked(player, SkillRegistry.SNEAK_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SNEAK_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.05F;
                            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER, SkillRegistry.SNEAK_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
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
