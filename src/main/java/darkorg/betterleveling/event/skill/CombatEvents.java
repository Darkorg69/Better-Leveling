package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CombatEvents {
    @SubscribeEvent
    public static void onStrength(LivingHurtEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (directEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) directEntity;
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(player, SkillRegistry.STRENGTH)) {
                    int currentLevel = capability.getLevel(player, SkillRegistry.STRENGTH);
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
        PlayerEntity player = event.getPlayer();
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
        Entity entity = event.getEntity();
        Item item = event.getItem().getItem();
        if (entity instanceof PlayerEntity && item instanceof BowItem) {
            PlayerEntity player = (PlayerEntity) entity;
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
    public static void onArrowSpeed(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity) entity;
            Entity owner = arrow.getOwner();
            if (owner instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) owner;
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
    }

    @SubscribeEvent
    public static void onIronSkin(LivingHurtEvent event) {
        LivingEntity entityLiving = event.getEntityLiving();
        if (entityLiving instanceof PlayerEntity && event.getSource() != DamageSource.FALL) {
            PlayerEntity player = (PlayerEntity) entityLiving;
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
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SkillRegistry.SNEAK_SPEED)) {
                        int currentLevel = capability.getLevel(player, SkillRegistry.SNEAK_SPEED);
                        if (currentLevel > 0) {
                            ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
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
