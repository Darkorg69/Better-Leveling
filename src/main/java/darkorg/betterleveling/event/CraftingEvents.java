package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CraftingEvents {
    private static int tick;

    @SubscribeEvent
    public static void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            if (!StackUtil.isBlacklistCrafting(event.getCrafting())) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.getUnlocked(serverPlayer, Specializations.CRAFTING.get())) {
                        List<ItemStack> ingredients = CraftingUtil.getIngredients(event.getInventory());
                        if (ingredients.size() > 1) {
                            Random random = new Random();
                            int sum = ingredients.stream().mapToInt(ItemStack::getCount).sum();
                            double currentBonus = MathHelper.nextDouble(random, 0.5D, ModConfig.SPECIALIZATIONS.combatBonus.get());
                            serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(sum * currentBonus)));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onGreenThumb(PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.END && event.player instanceof ServerPlayerEntity) {
            tick++;
            if (tick >= ModConfig.SKILLS.greenThumbTickRate.get()) {
                tick = 0;
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.GREEN_THUMB.get();
                    if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                        int currentLevel = capability.getLevel(serverPlayer, skill);
                        if (currentLevel > 0) {
                            ServerWorld serverLevel = serverPlayer.getLevel();
                            if (serverLevel.random.nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                                BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().inflate(16, 16, 16)).forEach(pos -> {
                                    BlockState state = serverLevel.getBlockState(pos);
                                    if (StateUtil.isBonemealablePlant(pos, serverLevel)) {
                                        state.randomTick(serverLevel, pos, serverLevel.random);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onHarvestProficiency(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            if (!serverPlayer.isCreative()) {
                BlockPos pos = event.getPos();
                ServerWorld serverLevel = serverPlayer.getLevel();
                if (StateUtil.isBonemealablePlant(pos, serverLevel)) {
                    BlockState state = event.getState();
                    if (StateUtil.isMaxAgeBonemealableBlock(pos, serverLevel, (IGrowable) state.getBlock())) {
                        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                            Skill skill = Skills.HARVEST_PROFICIENCY.get();
                            if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                                int currentLevel = capability.getLevel(serverPlayer, skill);
                                if (currentLevel > 0) {
                                    Random random = new Random();
                                    if (random.nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                                        List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                                        drops.forEach(stack -> {
                                            if (StackUtil.isCrop(stack)) {
                                                int originalCount = stack.getCount();
                                                int potentialLoot = TreasureUtil.getPotentialLoot(originalCount, ModConfig.SKILLS.harvestProficiencyPotentialLootBound.get());
                                                stack.setCount(potentialLoot);
                                            }
                                            Block.popResource(serverLevel, pos, stack);
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSkinning(LivingDropsEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (directEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) directEntity;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.SKINNING.get();
                if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                    int currentLevel = capability.getLevel(serverPlayer, skill);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                            for (ItemEntity itemEntity : event.getDrops()) {
                                ItemStack stack = itemEntity.getItem();
                                if (StackUtil.isSkin(stack)) {
                                    int originalCount = stack.getCount();
                                    int potentialLoot = TreasureUtil.getPotentialLoot(originalCount, ModConfig.SKILLS.skinningPotentialLootBound.get());
                                    stack.grow(potentialLoot);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        Entity entity = event.getSource().getDirectEntity();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                Skill skill = Skills.MEAT_GATHERING.get();
                if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                    int currentLevel = capability.getLevel(serverPlayer, skill);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                            for (ItemEntity itemEntity : event.getDrops()) {
                                ItemStack stack = itemEntity.getItem();
                                if (StackUtil.isMeat(stack)) {
                                    int count = stack.getCount();
                                    int potentialLoot = TreasureUtil.getPotentialLoot(count, ModConfig.SKILLS.meatGatheringPotentialLootBound.get());
                                    stack.grow(potentialLoot);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSwimSpeed(PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.START && event.player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.player;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (SkillUtil.hasUnlocked(capability, serverPlayer, Skills.SWIM_SPEED.get())) {
                    int currentLevel = capability.getLevel(serverPlayer, Skills.SWIM_SPEED.get());
                    if (currentLevel > 0) {
                        ModifiableAttributeInstance attribute = serverPlayer.getAttribute(ForgeMod.SWIM_SPEED.get());
                        if (attribute != null) {
                            AttributeModifier modifier = attribute.getModifier(AttributeModifiers.SWIM_SPEED_MODIFIER);
                            if (serverPlayer.isSwimming()) {
                                if (modifier == null) {
                                    attribute.addTransientModifier(new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, Skills.SWIM_SPEED.get().getName(), Skills.SWIM_SPEED.get().getCurrentBonus(currentLevel), AttributeModifier.Operation.MULTIPLY_BASE));
                                }
                                return;
                            }
                            if (modifier != null) {
                                attribute.removeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER);
                            }
                        }
                    }
                }
            });
        }
    }
}
