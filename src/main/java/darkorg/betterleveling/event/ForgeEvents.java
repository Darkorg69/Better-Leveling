package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.command.MaxCommand;
import darkorg.betterleveling.command.ResetCommand;
import darkorg.betterleveling.command.SetSkillCommand;
import darkorg.betterleveling.command.SetSpecializationCommand;
import darkorg.betterleveling.config.ServerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity object = event.getObject();
        if (object instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) object;
            if (!player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "player"), new PlayerCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<TileEntity> event) {
        TileEntity object = event.getObject();
        if (object instanceof AbstractFurnaceTileEntity) {
            AbstractFurnaceTileEntity tileEntity = (AbstractFurnaceTileEntity) object;
            if (!tileEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).isPresent()) {
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "block"), new MachineCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
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
            PlayerEntity original = event.getOriginal();
            original.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(oldCap -> {
                event.getEntity().getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(newCap -> {
                    newCap.setNBTData(oldCap.getNBTData());
                });
            });
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
}
