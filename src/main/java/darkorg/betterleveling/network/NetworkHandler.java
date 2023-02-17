package darkorg.betterleveling.network;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.network.packets.AddSpecC2SPacket;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
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
        instance.messageBuilder(AddSpecC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(AddSpecC2SPacket::new).encoder(AddSpecC2SPacket::encode).consumerMainThread(AddSpecC2SPacket::handle).add();
        instance.messageBuilder(AddSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(AddSkillC2SPacket::new).encoder(AddSkillC2SPacket::encode).consumerMainThread(AddSkillC2SPacket::handle).add();
        instance.messageBuilder(SyncDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(SyncDataS2CPacket::new).encoder(SyncDataS2CPacket::encode).consumerMainThread(SyncDataS2CPacket::handle).add();
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