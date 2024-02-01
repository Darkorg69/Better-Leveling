package darkorg.betterleveling.network.packets.specialization;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SpecializationsScreen;
import darkorg.betterleveling.util.LocalPlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class RefreshSpecializationScreenS2CPacket {
    public RefreshSpecializationScreenS2CPacket() {

    }

    public RefreshSpecializationScreenS2CPacket(FriendlyByteBuf pBuf) {

    }

    public static void encode(RefreshSpecializationScreenS2CPacket pPacket, FriendlyByteBuf pBuf) {

    }

    public static void handle(RefreshSpecializationScreenS2CPacket pPacket, CustomPayloadEvent.Context pContext) {
        // HERE WE ARE ON THE CLIENT!
        pContext.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> RefreshSpecializationScreenS2CPacket::handleOnClient));

        pContext.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleOnClient() {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                LocalPlayerUtil.updateExperienceOverlay(capability, localPlayer);
                RenderUtil.updateScreen(new SpecializationsScreen());
            });
        }
    }
}
