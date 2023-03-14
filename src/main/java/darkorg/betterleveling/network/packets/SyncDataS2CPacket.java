package darkorg.betterleveling.network.packets;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncDataS2CPacket {
    private final CompoundNBT data;

    public SyncDataS2CPacket(CompoundNBT pData) {
        this.data = pData;
    }

    public SyncDataS2CPacket(PacketBuffer pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(SyncDataS2CPacket pPacket, PacketBuffer pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(SyncDataS2CPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                // HERE WE ARE ON THE CLIENT!
                ClientPlayerEntity localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) {
                    localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        capability.receiveDataFromServer(pPacket.data);
                    });
                }
            });
        }
        context.setPacketHandled(true);
    }
}
