package darkorg.betterleveling.network.packets.specialization;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.SpecializationUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UnlockSpecializationC2SPacket {
    private final CompoundTag data;

    public UnlockSpecializationC2SPacket(Specialization pSpecialization) {
        this.data = new CompoundTag();
        this.data.putString("Name", pSpecialization.getName());
    }

    public UnlockSpecializationC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(UnlockSpecializationC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(UnlockSpecializationC2SPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();

        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
                    Specialization specialization = Specializations.getFrom(pPacket.data.getString("Name"));
                    if (!serverPlayer.isCreative()) {
                        serverPlayer.giveExperienceLevels(!SpecializationUtil.hasUnlocked(pCapability, serverPlayer) ? -ModConfig.GAMEPLAY.firstSpecCost.get() : -specialization.getProperties().getLevelCost());
                    }
                    pCapability.setUnlocked(serverPlayer, specialization, true);
                });
            }
        });

        context.setPacketHandled(true);
    }
}