package darkorg.betterleveling.network;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import darkorg.betterleveling.network.packets.skill.DecreaseSkillC2SPacket;
import darkorg.betterleveling.network.packets.skill.IncreaseSkillC2SPacket;
import darkorg.betterleveling.network.packets.skill.RefreshSkillScreenS2CPacket;
import darkorg.betterleveling.network.packets.skill.RequestSkillScreenUpdateC2SPacket;
import darkorg.betterleveling.network.packets.specialization.RefreshSpecializationScreenS2CPacket;
import darkorg.betterleveling.network.packets.specialization.RequestSpecializationsScreenC2SPacket;
import darkorg.betterleveling.network.packets.specialization.UnlockSpecializationC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static SimpleChannel INSTANCE;
    private static int id = 0;


    public static void init() {
        SimpleChannel instance = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(BetterLeveling.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();

        INSTANCE = instance;

        instance.messageBuilder(UnlockSpecializationC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(UnlockSpecializationC2SPacket::new).encoder(UnlockSpecializationC2SPacket::encode).consumer(UnlockSpecializationC2SPacket::handle).add();
        instance.messageBuilder(RequestSpecializationsScreenC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(RequestSpecializationsScreenC2SPacket::new).encoder(RequestSpecializationsScreenC2SPacket::encode).consumer(RequestSpecializationsScreenC2SPacket::handle).add();
        instance.messageBuilder(IncreaseSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(IncreaseSkillC2SPacket::new).encoder(IncreaseSkillC2SPacket::encode).consumer(IncreaseSkillC2SPacket::handle).add();
        instance.messageBuilder(DecreaseSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(DecreaseSkillC2SPacket::new).encoder(DecreaseSkillC2SPacket::encode).consumer(DecreaseSkillC2SPacket::handle).add();
        instance.messageBuilder(RequestSkillScreenUpdateC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(RequestSkillScreenUpdateC2SPacket::new).encoder(RequestSkillScreenUpdateC2SPacket::encode).consumer(RequestSkillScreenUpdateC2SPacket::handle).add();

        instance.messageBuilder(SyncDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(SyncDataS2CPacket::new).encoder(SyncDataS2CPacket::encode).consumer(SyncDataS2CPacket::handle).add();
        instance.messageBuilder(RefreshSpecializationScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(RefreshSpecializationScreenS2CPacket::new).encoder(RefreshSpecializationScreenS2CPacket::encode).consumer(RefreshSpecializationScreenS2CPacket::handle).add();
        instance.messageBuilder(RefreshSkillScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(RefreshSkillScreenS2CPacket::new).encoder(RefreshSkillScreenS2CPacket::encode).consumer(RefreshSkillScreenS2CPacket::handle).add();
    }

    private static int id() {
        return id++;
    }

    public static <MSG> void sendToServer(MSG pMessage) {
        INSTANCE.sendToServer(pMessage);
    }

    public static <MSG> void sendToPlayer(MSG pMessage, ServerPlayer pServerPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> pServerPlayer), pMessage);
    }
}