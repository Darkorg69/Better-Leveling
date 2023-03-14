package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CombatEvents {
    @SubscribeEvent
    public static void onCombat(LivingExperienceDropEvent event) {
        PlayerEntity attackingPlayer = event.getAttackingPlayer();
        if (attackingPlayer instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) attackingPlayer;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.getUnlocked(serverPlayer, Specializations.COMBAT.get())) {
                    double currentBonus = MathHelper.nextDouble(new Random(), 0.0D, ModConfig.SPECIALIZATIONS.combatBonus.get());
                    serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(event.getOriginalExperience() * currentBonus)));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onStrength(LivingHurtEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (directEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) directEntity;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.STRENGTH.get();
                if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                    int currentLevel = capability.getLevel(serverPlayer, skill);
                    if (currentLevel > 0) {
                        double currentBonus = 1.0D + skill.getCurrentBonus(currentLevel);
                        event.setAmount(event.getAmount() * (float) currentBonus);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onCriticalStrike(CriticalHitEvent event) {
        if (ModConfig.SKILLS.disableVanillaCrits.get() && event.isVanillaCritical()) {
            event.setResult(Event.Result.DENY);
        }
        PlayerEntity player = event.getPlayer();
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
            Skill skill = Skills.CRITICAL_STRIKE.get();
            if (SkillUtil.hasUnlocked(capability, player, skill)) {
                int currentLevel = capability.getLevel(player, skill);
                if (currentLevel > 0) {
                    if (player.getRandom().nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onQuickDraw(LivingEntityUseItemEvent.Start event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && event.getItem().getItem() instanceof BowItem) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (SkillUtil.hasUnlocked(capability, player, Skills.QUICK_DRAW.get())) {
                    int currentLevel = capability.getLevel(player, Skills.QUICK_DRAW.get());
                    if (currentLevel > 0) {
                        event.setDuration(event.getDuration() - Math.toIntExact(Math.round(20.0D * Skills.QUICK_DRAW.get().getCurrentBonus(currentLevel))));
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
            if (arrow.getOwner() instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) arrow.getOwner();
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (SkillUtil.hasUnlocked(capability, serverPlayer, Skills.ARROW_SPEED.get())) {
                        int currentLevel = capability.getLevel(serverPlayer, Skills.ARROW_SPEED.get());
                        if (currentLevel > 0) {
                            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1.0D + Skills.ARROW_SPEED.get().getCurrentBonus(currentLevel)));
                            arrow.canUpdate();
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onIronSkin(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && event.getSource() != DamageSource.FALL) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (SkillUtil.hasUnlocked(capability, player, Skills.IRON_SKIN.get())) {
                    int currentLevel = capability.getLevel(player, Skills.IRON_SKIN.get());
                    if (currentLevel > 0) {
                        event.setAmount(event.getAmount() * (float) Skills.IRON_SKIN.get().getCurrentBonus(currentLevel));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSneakSpeed(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && event.player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.player;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (SkillUtil.hasUnlocked(capability, serverPlayer, Skills.SNEAK_SPEED.get())) {
                    int currentLevel = capability.getLevel(serverPlayer, Skills.SNEAK_SPEED.get());
                    if (currentLevel > 0) {
                        ModifiableAttributeInstance attribute = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
                        if (attribute != null) {
                            if (serverPlayer.isCrouching()) {
                                if (attribute.getModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER) == null) {
                                    attribute.addTransientModifier(new AttributeModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER, Skills.SNEAK_SPEED.get().getName(), Skills.SNEAK_SPEED.get().getCurrentBonus(currentLevel), AttributeModifier.Operation.MULTIPLY_BASE));
                                }
                            } else {
                                if (attribute.getModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER) != null) {
                                    attribute.removeModifier(AttributeModifiers.SNEAK_SPEED_MODIFIER);
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
