package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class IncreaseSkillC2SPacket {
    private final CompoundNBT data;

    public IncreaseSkillC2SPacket(Skill pSkill) {
        this.data = new CompoundNBT();
        this.data.putString("Name", pSkill.getName());
    }

    public IncreaseSkillC2SPacket(PacketBuffer pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(IncreaseSkillC2SPacket pPacket, PacketBuffer pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(IncreaseSkillC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerEntity serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.getFrom(pPacket.data.getString("Name"));
                    if (!serverPlayer.isCreative()) {
                        serverPlayer.giveExperiencePoints(-skill.getCurrentCost(capability.getLevel(serverPlayer, skill)));
                    }
                    capability.addLevel(serverPlayer, skill, 1);
                });
            }
        });
        context.setPacketHandled(true);
    }
}