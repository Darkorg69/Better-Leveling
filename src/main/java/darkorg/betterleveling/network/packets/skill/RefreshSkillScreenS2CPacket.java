package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SkillScreen;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.LocalPlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class RefreshSkillScreenS2CPacket {
    private final CompoundTag data;

    public RefreshSkillScreenS2CPacket(Skill pSkill) {
        this.data = new CompoundTag();
        this.data.putString("Name", pSkill.getName());
    }

    public RefreshSkillScreenS2CPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(RefreshSkillScreenS2CPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(RefreshSkillScreenS2CPacket pPacket, CustomPayloadEvent.Context pContext) {
        // HERE WE ARE ON THE CLIENT!
        pContext.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(pPacket)));

        pContext.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleOnClient(RefreshSkillScreenS2CPacket pPacket) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                LocalPlayerUtil.updateExperienceOverlay(capability, localPlayer);
                RenderUtil.updateScreen(new SkillScreen(Skills.getFrom(pPacket.data.getString("Name"))));
            });
        }
    }
}
