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

    /**
     * Packet constructor.
     */
    public SyncDataS2CPacket(CompoundNBT pData) {
        this.data = pData;
    }

    /**
     * Decodes data from the packet buffer
     */
    public SyncDataS2CPacket(PacketBuffer packetBuffer) {
        this.data = packetBuffer.readNbt();
    }

    /**
     * Encodes data to the packet buffer
     */
    public static void encode(SyncDataS2CPacket packet, PacketBuffer packetBuffer) {
        packetBuffer.writeNbt(packet.data);
    }

    /**
     * Handles the packet functionality
     */
    public static void handle(SyncDataS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                // HERE WE ARE ON THE CLIENT!
                ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;

                if (clientPlayer != null) {
                    clientPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        playerCapability.receiveDataFromServer(packet.data);
                    });
                }
            });
        }
        context.setPacketHandled(true);
    }
}
