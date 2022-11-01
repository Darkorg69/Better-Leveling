package darkorg.betterleveling.network;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.network.packets.AddSkillC2SPacket;
import darkorg.betterleveling.network.packets.AddSpecC2SPacket;
import darkorg.betterleveling.network.packets.SyncDataS2CPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static SimpleChannel INSTANCE;

    private static int id = 0;

    private static int id() {
        return id++;
    }

    public static void init() {
        SimpleChannel simpleChannel = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(BetterLeveling.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();

        INSTANCE = simpleChannel;

        //Client to server packets
        simpleChannel.messageBuilder(AddSpecC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddSpecC2SPacket::new)
                .encoder(AddSpecC2SPacket::encode)
                .consumer(AddSpecC2SPacket::handle)
                .add();
        simpleChannel.messageBuilder(AddSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddSkillC2SPacket::new)
                .encoder(AddSkillC2SPacket::encode)
                .consumer(AddSkillC2SPacket::handle)
                .add();
        //Server to client packets
        simpleChannel.messageBuilder(SyncDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncDataS2CPacket::new)
                .encoder(SyncDataS2CPacket::encode)
                .consumer(SyncDataS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}