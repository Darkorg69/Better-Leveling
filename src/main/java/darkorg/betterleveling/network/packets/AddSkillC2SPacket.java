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

    public AddSkillC2SPacket(Pair<ISkill, Integer> pPair) {
        this.data = new CompoundNBT();
        this.data.putString("Skill", pPair.getFirst().getName());
        this.data.putInt("Value", pPair.getSecond());
    }

    public AddSkillC2SPacket(PacketBuffer buf) {
        this.data = buf.readNbt();
    }

    public static void encode(AddSkillC2SPacket packet, PacketBuffer buf) {
        buf.writeNbt(packet.data);
    }

    public static void handle(AddSkillC2SPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    capability.addLevel(serverPlayer, CapabilityUtil.getSkillFromName(packet.data.getString("Skill")), packet.data.getInt("Value"));
                });
            }
        });
        context.setPacketHandled(true);
    }
}
