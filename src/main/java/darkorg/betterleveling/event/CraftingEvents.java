package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
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
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!StackUtil.isBlacklistCrafting(event.getCrafting())) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.getUnlocked(serverPlayer, Specializations.CRAFTING.get())) {
                        List<ItemStack> ingredients = CraftingUtil.getIngredients(event.getInventory());
                        if (ingredients.size() > 1) {
                            Random random = new Random();
                            int sum = ingredients.stream().mapToInt(ItemStack::getCount).sum();
                            double currentBonus = random.nextDouble(0.5D, ModConfig.SPECIALIZATIONS.craftingBonus.get());
                            serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(sum * currentBonus)));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onGreenThumb(PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.END && event.player instanceof ServerPlayer) {
            tick++;
            if (tick >= ModConfig.SKILLS.greenThumbTickRate.get()) {
                tick = 0;
                ServerPlayer serverPlayer = (ServerPlayer) event.player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.GREEN_THUMB.get();
                    if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                        int currentLevel = capability.getLevel(serverPlayer, skill);
                        if (currentLevel > 0) {
                            ServerLevel serverLevel = serverPlayer.serverLevel();
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
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                BlockPos pos = event.getPos();
                ServerLevel serverLevel = serverPlayer.serverLevel();
                if (StateUtil.isBonemealablePlant(pos, serverLevel)) {
                    BlockState state = event.getState();
                    if (StateUtil.isMaxAgeBonemealableBlock(pos, serverLevel, (BonemealableBlock) state.getBlock())) {
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
        if (directEntity instanceof ServerPlayer serverPlayer) {
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
        if (entity instanceof ServerPlayer serverPlayer) {
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
        if (event.side == LogicalSide.SERVER && event.phase == Phase.START && event.player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (SkillUtil.hasUnlocked(capability, serverPlayer, Skills.SWIM_SPEED.get())) {
                    int currentLevel = capability.getLevel(serverPlayer, Skills.SWIM_SPEED.get());
                    if (currentLevel > 0) {
                        AttributeInstance attribute = serverPlayer.getAttribute(ForgeMod.SWIM_SPEED.get());
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
