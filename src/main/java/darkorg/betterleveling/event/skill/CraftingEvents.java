package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
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

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CraftingEvents {
    private static int tick;

    @SubscribeEvent
    public static void onGreenThumb(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.player instanceof ServerPlayer) {
            if (tick < 100) tick++;
            else {
                tick = 0;
                ServerPlayer serverPlayer = (ServerPlayer) event.player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(serverPlayer, SkillRegistry.GREEN_THUMB)) {
                        int level = capability.getLevel(serverPlayer, SkillRegistry.GREEN_THUMB);
                        if (level > 0) {
                            float chance = level * 0.025F;
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            if (serverLevel.random.nextFloat() <= chance) {
                                BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().inflate(8, 8, 8)).forEach(pos -> {
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

    @SubscribeEvent
    public static void onHarvestProficiency(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            BlockState state = event.getState();
            if (state.is(ModTags.Blocks.CROPS) && state.getBlock() instanceof BonemealableBlock block) {
                BlockPos pos = event.getPos();
                ServerLevel serverLevel = serverPlayer.getLevel();
                if (!block.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.isUnlocked(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY)) {
                            int level = capability.getLevel(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY);
                            if (level > 0) {
                                List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null);
                                drops.forEach(stack -> {
                                    if (stack.is(ModTags.Items.CROPS)) {
                                        stack.setCount(Math.round(stack.getCount() * serverLevel.random.nextFloat(0, level * 0.5F)));
                                        Block.popResource(serverLevel, pos, stack);
                                    }
                                });
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
                if (capability.isUnlocked(serverPlayer, SkillRegistry.SKINNING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.SKINNING);
                    if (level > 0) {
                        Random random = new Random();
                        Collection<ItemEntity> drops = event.getDrops();
                        drops.forEach(entity -> {
                            ItemStack stack = entity.getItem();
                            if (stack.is(ModTags.Items.ANIMAL_SKIN)) {
                                int count = stack.getCount();
                                stack.setCount(count + Math.round(count * random.nextFloat(0, level * 0.5F)));
                            }
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.MEAT_GATHERING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.MEAT_GATHERING);
                    if (level > 0) {
                        Random random = new Random();
                        Collection<ItemEntity> drops = event.getDrops();
                        drops.forEach(entity -> {
                            ItemStack stack = entity.getItem();
                            if (stack.is(ModTags.Items.ANIMAL_MEAT)) {
                                int count = stack.getCount();
                                stack.setCount(count + Math.round(count * random.nextFloat(0, level * 0.5F)));
                            }
                        });
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
                    if (capability.isUnlocked(player, SkillRegistry.SWIM_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SWIM_SPEED);
                        if (level > 0) {
                            AttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, SkillRegistry.SWIM_SPEED.getName(), level * 0.1F, AttributeModifier.Operation.MULTIPLY_BASE);
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
