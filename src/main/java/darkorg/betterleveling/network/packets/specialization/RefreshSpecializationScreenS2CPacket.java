package darkorg.betterleveling.network.packets.specialization;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SpecializationsScreen;
import darkorg.betterleveling.util.LocalPlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RefreshSpecializationScreenS2CPacket {
    public RefreshSpecializationScreenS2CPacket() {

    }

    public RefreshSpecializationScreenS2CPacket(PacketBuffer pBuf) {

    }

    public static void encode(RefreshSpecializationScreenS2CPacket pPacket, PacketBuffer pBuf) {

    }

    public static void handle(RefreshSpecializationScreenS2CPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        // HERE WE ARE ON THE CLIENT!
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> RefreshSpecializationScreenS2CPacket::handleOnClient);
        });

        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleOnClient() {
        ClientPlayerEntity localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                LocalPlayerUtil.updateExperienceOverlay(capability, localPlayer);
                RenderUtil.updateScreen(new SpecializationsScreen());
            });
        }
    }
}
