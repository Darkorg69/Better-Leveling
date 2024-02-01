package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.BlockEntityCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.command.MaxPlayerCommand;
import darkorg.betterleveling.command.ResetPlayerCommand;
import darkorg.betterleveling.command.SetSkillCommand;
import darkorg.betterleveling.command.SetSpecializationCommand;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player player) {
            if (!player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "player"), new PlayerCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        BlockEntity tileEntity = event.getObject();
        if (tileEntity instanceof AbstractFurnaceBlockEntity blockEntity) {
            if (!blockEntity.getCapability(BlockEntityCapabilityProvider.BLOCK_ENTITY_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "block_entity"), new BlockEntityCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        new MaxPlayerCommand(event.getDispatcher());
        new ResetPlayerCommand(event.getDispatcher());
        new SetSkillCommand(event.getDispatcher());
        new SetSpecializationCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.level().isClientSide) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> capability.sendDataToPlayer(serverPlayer));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        if (oldPlayer instanceof ServerPlayer oldServerPlayer) {
            oldServerPlayer.reviveCaps();
            oldServerPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(oldCap -> {
                Player newPlayer = event.getEntity();
                if (newPlayer instanceof ServerPlayer newServerPlayer) {
                    newServerPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(newCap -> {
                        CompoundTag oldData = oldCap.getData();
                        if (!event.isWasDeath()) {
                            newCap.setData(oldData);
                        } else {
                            if (!ModConfig.GAMEPLAY.resetOnDeath.get()) {
                                oldData.putInt("AvailableXP", 0);
                                newCap.setData(oldData);
                            }
                        }
                    });
                }
            });
            oldServerPlayer.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRightClickHarvest(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer && ModConfig.GAMEPLAY.rightClickHarvest.get()) {
            BlockPos pos = event.getPos();
            BlockState state = serverLevel.getBlockState(pos);
            if (StateUtil.isBonemealablePlant(pos, serverLevel)) {
                Block block = state.getBlock();
                if (StateUtil.isMaxAgeBonemealableBlock(pos, serverLevel, (BonemealableBlock) block)) {
                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        Skill skill = Skills.HARVEST_PROFICIENCY.get();
                        if (SkillUtil.hasUnlocked(capability, serverPlayer, skill)) {
                            int currentLevel = capability.getLevel(serverPlayer, skill);
                            if (currentLevel > 0) {
                                Random random = new Random();
                                if (random.nextDouble() <= skill.getCurrentBonus(currentLevel)) {
                                    for (ItemStack stack : drops) {
                                        if (StackUtil.isCrop(stack)) {
                                            int originalCount = stack.getCount();
                                            int potentialLoot = TreasureUtil.getPotentialLoot(originalCount, ModConfig.SKILLS.harvestProficiencyPotentialLootBound.get());
                                            stack.grow(potentialLoot);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    for (ItemStack stack : drops) {
                        if (StackUtil.isSeed(stack)) {
                            stack.shrink(1);
                        }
                        Block.popResource(serverLevel, pos, stack);
                    }
                    serverLevel.setBlockAndUpdate(pos, block.defaultBlockState());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickFurnace(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();
        if (event.getSide() == LogicalSide.SERVER && entity instanceof ServerPlayer serverPlayer) {
            BlockEntity tileEntity = event.getLevel().getBlockEntity(event.getPos());
            if (tileEntity instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
                abstractFurnaceBlockEntity.getCapability(BlockEntityCapabilityProvider.BLOCK_ENTITY_CAP).ifPresent(capability -> {
                    ItemStack stack = event.getItemStack();
                    if (capability.hasOwner()) {
                        if (capability.isOwner(serverPlayer) && PlayerUtil.isCrouchingWithEmptyHand(serverPlayer, stack)) {
                            capability.removeOwner();
                            serverPlayer.displayClientMessage(ModComponents.UNREGISTER, true);
                            event.setCanceled(true);
                            return;
                        }
                        if (PlayerUtil.isCrouchingWithEmptyHand(serverPlayer, stack)) {
                            serverPlayer.displayClientMessage(ModComponents.NOT_OWNED, true);
                            event.setCanceled(true);
                            return;
                        }
                        if (ModConfig.GAMEPLAY.lockBoundMachines.get()) {
                            serverPlayer.displayClientMessage(ModComponents.NO_ACCESS, true);
                            event.setCanceled(true);
                            return;
                        }
                    }
                    if (PlayerUtil.isCrouchingWithEmptyHand(serverPlayer, stack)) {
                        capability.setOwner(serverPlayer);
                        serverPlayer.displayClientMessage(ModComponents.REGISTER, true);
                    }
                });
            }
        }
    }
}