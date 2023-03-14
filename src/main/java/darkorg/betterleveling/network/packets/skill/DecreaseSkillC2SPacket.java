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

public class DecreaseSkillC2SPacket {
    private final CompoundNBT data;

    public DecreaseSkillC2SPacket(Skill pSkill) {
        this.data = new CompoundNBT();
        this.data.putString("Skill", pSkill.getName());
    }

    public DecreaseSkillC2SPacket(PacketBuffer pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(DecreaseSkillC2SPacket pPacket, PacketBuffer pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(DecreaseSkillC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.getFrom(pPacket.data.getString("Skill"));
                    if (!serverPlayer.isCreative()) {
                        serverPlayer.giveExperiencePoints(skill.getCurrentRefund(capability.getLevel(serverPlayer, skill)));
                    }
                    capability.addLevel(serverPlayer, skill, -1);
                    NetworkHandler.sendToPlayer(new RefreshSkillScreenS2CPacket(skill), serverPlayer);
                });
            }
        });
        context.setPacketHandled(true);
    }
}