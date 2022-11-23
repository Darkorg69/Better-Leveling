package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.command.MaxCommand;
import darkorg.betterleveling.command.ResetCommand;
import darkorg.betterleveling.command.SetSkillCommand;
import darkorg.betterleveling.command.SetSpecializationCommand;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "player"), new PlayerCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof AbstractFurnaceBlockEntity tileEntity) {
            if (!tileEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "machine"), new MachineCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.level.isClientSide) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    capability.sendDataToPlayer(serverPlayer);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !ServerConfig.RESET_ON_DEATH.get()) {
            Player original = event.getOriginal();
            original.reviveCaps();
            original.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(oldCap -> {
                event.getEntity().getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(newCap -> {
                    newCap.setNBTData(oldCap.getNBTData());
                });
            });
            original.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        new MaxCommand(event.getDispatcher());
        new ResetCommand(event.getDispatcher());
        new SetSkillCommand(event.getDispatcher());
        new SetSpecializationCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onSimpleHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (ServerConfig.SIMPLE_HARVEST.get()) {
            if (event.getWorld() instanceof ServerLevel serverLevel) {
                if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                    BlockPos pos = event.getPos();
                    BlockState state = serverLevel.getBlockState(pos);
                    Block block = state.getBlock();
                    if (state.is(ModTags.Blocks.CROPS) && block instanceof BonemealableBlock growable) {
                        if (!growable.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null);
                            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                                if (capability.isUnlocked(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY)) {
                                    int level = capability.getLevel(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY);
                                    drops.forEach(stack -> {
                                        if (stack.is(ModTags.Items.CROPS)) {
                                            stack.setCount(stack.getCount() + Math.round(stack.getCount() * serverLevel.random.nextFloat(0, level * 0.5F)));
                                        }
                                    });
                                }
                            });
                            drops.forEach(stack -> {
                                if (!stack.is(ModTags.Items.CROPS)) {
                                    stack.setCount(stack.getCount() - 1);
                                }
                                Block.popResource(serverLevel, pos, stack);
                            });
                            serverLevel.setBlockAndUpdate(pos, block.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
