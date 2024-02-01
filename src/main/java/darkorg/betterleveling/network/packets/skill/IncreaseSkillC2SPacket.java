package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class IncreaseSkillC2SPacket {
    private final CompoundTag data;

    public IncreaseSkillC2SPacket(Skill pSkill) {
        this.data = new CompoundTag();
        this.data.putString("Name", pSkill.getName());
    }

    public IncreaseSkillC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(IncreaseSkillC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(IncreaseSkillC2SPacket pPacket, CustomPayloadEvent.Context pContext) {
        pContext.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = pContext.getSender();
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
        pContext.setPacketHandled(true);
    }
}