package darkorg.betterleveling.network.packets;

import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.util.RegistryUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSpecC2SPacket {
    private final CompoundTag data;

    public AddSpecC2SPacket(Pair<ISpecialization, Boolean> pPair) {
        this.data = new CompoundTag();
        this.data.putString("Spec", pPair.getFirst().getName());
        this.data.putBoolean("Value", pPair.getSecond());
    }

    public AddSpecC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(AddSpecC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(AddSpecC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    capability.addUnlocked(serverPlayer, RegistryUtil.getSpecFromName(pPacket.data.getString("Spec")), pPacket.data.getBoolean("Value"));
                });
            }
        });
        context.setPacketHandled(true);
    }
}