package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.TreasureUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static darkorg.betterleveling.registry.SkillRegistry.*;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CraftingEvents {
    private static int tickCount;

    @SubscribeEvent
    public static void onGreenThumb(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            tickCount++;
            if (tickCount > ServerConfig.GREEN_THUMB_TICK_RATE.get()) {
                tickCount = 0;
                if (event.player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.hasUnlocked(serverPlayer, GREEN_THUMB)) {
                            int currentLevel = capability.getLevel(serverPlayer, GREEN_THUMB);
                            if (currentLevel > 0) {
                                ServerLevel serverLevel = serverPlayer.getLevel();
                                if (serverLevel.random.nextDouble() <= SkillUtil.getCurrentChance(GREEN_THUMB, currentLevel)) {
                                    BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().inflate(16, 16, 16)).forEach(pos -> {
                                        BlockState state = serverLevel.getBlockState(pos);
                                        if (state.getBlock() instanceof IPlantable plant) {
                                            PlantType type = plant.getPlantType(serverLevel, pos);
                                            if (type == PlantType.CROP || type == PlantType.NETHER) {
                                                state.randomTick(serverLevel, pos, serverLevel.random);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHarvestProficiency(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            BlockState state = event.getState();
            if (state.getBlock() instanceof BonemealableBlock bonemealableBlock) {
                BlockPos pos = event.getPos();
                ServerLevel serverLevel = serverPlayer.getLevel();
                if (!bonemealableBlock.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.hasUnlocked(serverPlayer, HARVEST_PROFICIENCY)) {
                            int currentLevel = capability.getLevel(serverPlayer, HARVEST_PROFICIENCY);
                            if (currentLevel > 0) {
                                if (serverLevel.random.nextDouble() <= SkillUtil.getCurrentChance(HARVEST_PROFICIENCY, currentLevel)) {
                                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                                    drops.forEach(stack -> {
                                        if (stack.is(ModTags.Items.CROPS)) {
                                            stack.setCount(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.HARVEST_PROFICIENCY_POTENTIAL_LOOT_BOUND.get(), serverLevel.random));
                                            Block.popResource(serverLevel, pos, stack);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSkinning(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, SKINNING)) {
                    int currentLevel = capability.getLevel(serverPlayer, SKINNING);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= SkillUtil.getCurrentChance(SKINNING, currentLevel)) {
                            Collection<ItemEntity> drops = event.getDrops();
                            drops.forEach(itemEntity -> {
                                ItemStack stack = itemEntity.getItem();
                                if (stack.is(ModTags.Items.SKINS)) {
                                    int originalCount = stack.getCount();
                                    stack.setCount(originalCount + TreasureUtil.getPotentialLoot(originalCount, ServerConfig.SKINNING_POTENTIAL_LOOT_BOUND.get(), random));
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, MEAT_GATHERING)) {
                    int currentLevel = capability.getLevel(serverPlayer, MEAT_GATHERING);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= SkillUtil.getCurrentChance(MEAT_GATHERING, currentLevel)) {
                            Collection<ItemEntity> drops = event.getDrops();
                            drops.forEach(entity -> {
                                ItemStack stack = entity.getItem();
                                if (stack.is(ModTags.Items.MEATS)) {
                                    int originalCount = stack.getCount();
                                    stack.setCount(originalCount + TreasureUtil.getPotentialLoot(originalCount, ServerConfig.MEAT_GATHERING_POTENTIAL_LOOT_BOUND.get(), random));
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSwimSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SWIM_SPEED)) {
                        int currentLevel = capability.getLevel(player, SWIM_SPEED);
                        if (currentLevel > 0) {
                            AttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, SWIM_SPEED.getName(), SkillUtil.getCurrentBonus(SWIM_SPEED, currentLevel), AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSwimming()) {
                                    if (attribute.getModifier(AttributeModifiers.SWIM_SPEED_MODIFIER) == null) {
                                        attribute.addTransientModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(AttributeModifiers.SWIM_SPEED_MODIFIER) != null) {
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

    @SubscribeEvent
    public static void onCookingSpeed(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide() == LogicalSide.SERVER && event.getPlayer() instanceof ServerPlayer serverPlayer) {
            if (event.getWorld().getBlockEntity(event.getPos()) instanceof AbstractFurnaceBlockEntity blockEntity) {
                blockEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(capability -> {
                    if (capability.hasOwner()) {
                        if (capability.isOwner(serverPlayer)) {
                            if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                capability.removeOwner();
                                serverPlayer.displayClientMessage(ModComponents.UNREGISTER, true);
                                event.setCanceled(true);
                            }
                        } else {
                            if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                serverPlayer.displayClientMessage(ModComponents.NOT_OWNED, true);
                                event.setCanceled(true);
                            } else {
                                if (ServerConfig.LOCK_BOUND_MACHINES.get()) {
                                    serverPlayer.displayClientMessage(ModComponents.NO_ACCESS, true);
                                    event.setCanceled(true);
                                }
                            }
                        }
                    } else {
                        if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                            capability.setOwner(serverPlayer);
                            serverPlayer.displayClientMessage(ModComponents.REGISTER, true);
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }
    }
}
