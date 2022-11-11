package darkorg.betterleveling.network.packets;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncDataS2CPacket {
    private final CompoundTag data;

    public SyncDataS2CPacket(CompoundTag pData) {
        this.data = pData;
    }

    public SyncDataS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public static void encode(SyncDataS2CPacket packet, FriendlyByteBuf buf) {
        buf.writeNbt(packet.data);
    }

    public static void handle(SyncDataS2CPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                // HERE WE ARE ON THE CLIENT!
                LocalPlayer localPlayer = Minecraft.getInstance().player;
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
