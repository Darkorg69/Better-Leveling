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

    public SyncDataS2CPacket(PacketBuffer buf) {
        this.data = buf.readNbt();
    }

    public static void encode(SyncDataS2CPacket packet, PacketBuffer buf) {
        buf.writeNbt(packet.data);
    }

    public static void handle(SyncDataS2CPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                // HERE WE ARE ON THE CLIENT!
                ClientPlayerEntity localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) {
                    localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        playerCapability.receiveDataFromServer(packet.data);
                    });
                }
            });
        }
        context.setPacketHandled(true);
    }
}
