package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class DecreaseSkillC2SPacket {
    private final CompoundTag data;

    public DecreaseSkillC2SPacket(Skill pSkill) {
        this.data = new CompoundTag();
        this.data.putString("Skill", pSkill.getName());
    }

    public DecreaseSkillC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(DecreaseSkillC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(DecreaseSkillC2SPacket pPacket, CustomPayloadEvent.Context pContext) {
        pContext.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = pContext.getSender();
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
        pContext.setPacketHandled(true);
    }
}