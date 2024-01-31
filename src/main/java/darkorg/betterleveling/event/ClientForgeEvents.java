package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.screen.ChooseSpecializationScreen;
import darkorg.betterleveling.gui.screen.SpecializationsScreen;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.util.PlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SpecializationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyMappings.OPEN_GUI.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localPlayer = minecraft.player;
            if (localPlayer != null) {
                localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (SpecializationUtil.hasUnlocked(capability, localPlayer)) {
                        minecraft.setScreen(new SpecializationsScreen());
                    } else {
                        if (PlayerUtil.canUnlockFirstSpecialization(localPlayer)) {
                            minecraft.setScreen(new ChooseSpecializationScreen());
                        } else {
                            localPlayer.displayClientMessage(RenderUtil.getCannotUnlock(), true);
                        }
                    }
                });
            }
        }
    }
}
