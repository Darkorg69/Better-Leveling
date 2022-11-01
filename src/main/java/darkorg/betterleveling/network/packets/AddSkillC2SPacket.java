package darkorg.betterleveling.network.packets;

import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSkillC2SPacket {
    private final CompoundNBT data;

    /**
     * Packet constructor
     *
     * @param pPair - Pair of the skill and the value to add to the level.
     */
    public AddSkillC2SPacket(Pair<ISkill, Integer> pPair) {
        this.data = new CompoundNBT();

        this.data.putString("Skill", pPair.getFirst().getName());

        this.data.putInt("Value", pPair.getSecond());
    }

    /**
     * Decodes data from the packet buffer
     *
     * @param packetBuffer Packet buffer.
     */
    public AddSkillC2SPacket(PacketBuffer packetBuffer) {
        this.data = packetBuffer.readCompoundTag();
    }

    /**
     * Encodes data to the packet buffer
     *
     * @param packetBuffer Packet buffer.
     */
    public static void encode(AddSkillC2SPacket packet, PacketBuffer packetBuffer) {
        packetBuffer.writeCompoundTag(packet.data);
    }

    /**
     * Handles the packet functionality
     */
    public static void handle(AddSkillC2SPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity serverPlayer = context.getSender();
            ISkill skill = CapabilityUtil.getSkillFromName(packet.data.getString("Skill"));
            int value = packet.data.getInt("Value");

            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    capability.addLevel(serverPlayer, skill, value);
                });
            }
        });
        context.setPacketHandled(true);
    }
}
