package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestSkillScreenUpdateC2SPacket {
    private final CompoundNBT data;

    public RequestSkillScreenUpdateC2SPacket(Skill pSkill) {
        this.data = new CompoundNBT();
        this.data.putString("Name", pSkill.getName());
    }

    public RequestSkillScreenUpdateC2SPacket(PacketBuffer pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(RequestSkillScreenUpdateC2SPacket pPacket, PacketBuffer pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(RequestSkillScreenUpdateC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    String name = pPacket.data.getString("Name");
                    NetworkHandler.sendToPlayer(new RefreshSkillScreenS2CPacket(Skills.getFrom(name)), serverPlayer);
                });
            }
        });
        context.setPacketHandled(true);
    }
}
