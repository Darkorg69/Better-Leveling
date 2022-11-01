package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.capability.TileEntityCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static darkorg.betterleveling.network.chat.ModTextComponents.*;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            if (!playerEntity.getCapability(PlayerCapabilityProvider.PLAYER_CAP).isPresent()) {
                PlayerCapabilityProvider provider = new PlayerCapabilityProvider();
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "player"), new PlayerCapabilityProvider());
                event.addListener(provider::invalidate);
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesTileEntity(AttachCapabilitiesEvent<TileEntity> event) {
        TileEntity tileEntity = event.getObject();
        if (tileEntity instanceof AbstractFurnaceTileEntity) {
            AbstractFurnaceTileEntity abstractFurnaceTileEntity = (AbstractFurnaceTileEntity) tileEntity;
            if (!abstractFurnaceTileEntity.getCapability(TileEntityCapabilityProvider.TILE_CAP).isPresent()) {
                TileEntityCapabilityProvider tileEntityCapabilityProvider = new TileEntityCapabilityProvider();
                event.addCapability(new ResourceLocation(BetterLeveling.MOD_ID, "tile_entity"), tileEntityCapabilityProvider);
                event.addListener(tileEntityCapabilityProvider::invalidate);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            if (!serverPlayer.getEntityWorld().isRemote()) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerData -> {
                    playerData.sendDataToPlayer(serverPlayer);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !ServerConfig.resetOnDeath.get()) {
            PlayerEntity original = event.getOriginal();
            original.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(oldCap -> {
                event.getEntity().getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(newCap -> {
                    newCap.setData(oldCap.getData());
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickFurnace(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide() == LogicalSide.SERVER) {
            PlayerEntity player = event.getPlayer();
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
                if (tileEntity instanceof AbstractFurnaceTileEntity) {
                    AbstractFurnaceTileEntity abstractFurnaceTileEntity = (AbstractFurnaceTileEntity) tileEntity;
                    abstractFurnaceTileEntity.getCapability(TileEntityCapabilityProvider.TILE_CAP).ifPresent(capability -> {
                        if (capability.hasOwner()) {
                            if (capability.isOwner(serverPlayer)) {
                                if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                    capability.removeOwner();
                                    serverPlayer.sendStatusMessage(UNREGISTER, true);
                                    event.setCanceled(true);
                                }
                            } else {
                                if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                    serverPlayer.sendStatusMessage(NOT_OWNED, true);
                                    event.setCanceled(true);
                                } else {
                                    if (ServerConfig.lockBoundFurnaces.get()) {
                                        serverPlayer.sendStatusMessage(NO_ACCESS, true);
                                        event.setCanceled(true);
                                    }
                                }
                            }
                        } else {
                            if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                capability.setOwner(serverPlayer);
                                serverPlayer.sendStatusMessage(REGISTER, true);
                                event.setCanceled(true);
                            }
                        }
                    });
                }
            }
        }
    }
}
