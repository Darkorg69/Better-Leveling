package darkorg.betterleveling.network.packets;

import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSpecC2SPacket {
    private final CompoundNBT data;

    /**
     * Packet constructor
     */
    public AddSpecC2SPacket(Pair<ISpecialization, Boolean> pPair) {
        this.data = new CompoundNBT();

        this.data.putString("Specialization", pPair.getFirst().getName());
        this.data.putBoolean("Value", pPair.getSecond());
    }

    /**
     * Decodes data from the packet buffer
     *
     * @param packetBuffer Packet buffer.
     */
    public AddSpecC2SPacket(PacketBuffer packetBuffer) {
        this.data = packetBuffer.readNbt();
    }

    /**
     * Encodes data to the packet buffer
     *
     * @param packetBuffer Packet buffer.
     */
    public static void encode(AddSpecC2SPacket packet, PacketBuffer packetBuffer) {
        packetBuffer.writeNbt(packet.data);
    }

    /**
     * Handles the packet functionality
     */
    public static void handle(AddSpecC2SPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity sender = context.getSender();
            ISpecialization specialization = CapabilityUtil.getSpecFromName(packet.data.getString("Specialization"));

            if (sender != null) {
                sender.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    boolean value = packet.data.getBoolean("Value");
                    capability.addUnlocked(sender, specialization, value);
                });
            }
        });
        context.setPacketHandled(true);
    }
}