package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.SkillScreen;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.LocalPlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RefreshSkillScreenS2CPacket {
    private final CompoundNBT data;

    public RefreshSkillScreenS2CPacket(Skill pSkill) {
        this.data = new CompoundNBT();
        this.data.putString("Name", pSkill.getName());
    }

    public RefreshSkillScreenS2CPacket(PacketBuffer pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(RefreshSkillScreenS2CPacket pPacket, PacketBuffer pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(RefreshSkillScreenS2CPacket pPacket, Supplier<NetworkEvent.Context> pSupplier) {
        NetworkEvent.Context context = pSupplier.get();
        // HERE WE ARE ON THE CLIENT!
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(pPacket));
        });

        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleOnClient(RefreshSkillScreenS2CPacket pPacket) {
        ClientPlayerEntity localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                LocalPlayerUtil.updateExperienceOverlay(capability, localPlayer);
                RenderUtil.updateScreen(new SkillScreen(Skills.getFrom(pPacket.data.getString("Name"))));
            });
        }
    }
}
