package darkorg.betterleveling.network.packets;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SyncDataS2CPacket {
    private final CompoundTag data;

    public SyncDataS2CPacket(CompoundTag pData) {
        this.data = pData;
    }

    public SyncDataS2CPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(SyncDataS2CPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(SyncDataS2CPacket pPacket, CustomPayloadEvent.Context pContext) {
        if (pContext.getDirection().getReceptionSide().isClient()) {
            pContext.enqueueWork(() -> {
                // HERE WE ARE ON THE CLIENT!
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) {
                    localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> capability.receiveDataFromServer(pPacket.data));
                }
            });
        }
        pContext.setPacketHandled(true);
    }
}
