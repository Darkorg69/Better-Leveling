package darkorg.betterleveling.network.packets.specialization;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.registry.Specializations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestSpecializationsScreenC2SPacket {
    private final CompoundTag data;

    public RequestSpecializationsScreenC2SPacket(Specialization pSpecialization) {
        this.data = new CompoundTag();

        if (pSpecialization != null) {
            this.data.putString("Name", pSpecialization.getName());
        }
    }

    public RequestSpecializationsScreenC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(RequestSpecializationsScreenC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(RequestSpecializationsScreenC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();

        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    String name = pPacket.data.getString("Name");
                    capability.setSpecialization(serverPlayer, name.isEmpty() ? null : Specializations.getFrom(name));
                    NetworkHandler.sendToPlayer(new RefreshSpecializationScreenS2CPacket(), serverPlayer);
                });
            }
        });

        context.setPacketHandled(true);
    }
}
