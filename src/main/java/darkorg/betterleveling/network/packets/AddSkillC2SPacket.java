package darkorg.betterleveling.network.packets;

import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSkillC2SPacket {
    private final CompoundTag data;

    public AddSkillC2SPacket(Pair<ISkill, Integer> pPair) {
        this.data = new CompoundTag();
        this.data.putString("Skill", pPair.getFirst().getName());
        this.data.putInt("Value", pPair.getSecond());
    }

    public AddSkillC2SPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public static void encode(AddSkillC2SPacket packet, FriendlyByteBuf buf) {
        buf.writeNbt(packet.data);
    }

    public static void handle(AddSkillC2SPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    capability.addLevel(serverPlayer, RegistryUtil.getSkillFromName(packet.data.getString("Skill")), packet.data.getInt("Value"));
                });
            }
        });
        context.setPacketHandled(true);
    }
}
