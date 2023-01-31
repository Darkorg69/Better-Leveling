package darkorg.betterleveling.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import darkorg.betterleveling.api.ISpecialization;
import darkorg.betterleveling.gui.widget.button.ChooseSpecButton;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.packets.AddSpecC2SPacket;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

import static darkorg.betterleveling.network.chat.ModComponents.*;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecScreen extends Screen {
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos, topPos;
    private ISpecialization playerSpecialization;

    public ChooseSpecScreen() {
        super(CHOOSE_SPEC_TITLE);
        this.playerSpecialization = SpecRegistry.getSpecRegistry().get(0);
    }

    @Override
    protected void init() {
        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        ChooseSpecButton chooseSpecButton = new ChooseSpecButton((this.width - 64) / 2, (this.height - 64) / 2 - 32, this.playerSpecialization, pPlayerSpecialization -> this.playerSpecialization = pPlayerSpecialization);
        addRenderableWidget(chooseSpecButton);

        ExtendedButton selectButton = new ExtendedButton((this.width - 75) / 2, this.topPos + 116, 75, 25, SELECT_BUTTON, this::onPress);
        addRenderableWidget(selectButton);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, font, title, (width / 2), this.topPos - 10, 16777215);
        RenderUtil.setShaderTexture();
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void onPress(Button pButton) {
        Minecraft.getInstance().setScreen(new ConfirmScreen((boolean pCallback) -> {
            if (pCallback) {
                NetworkHandler.sendToServer(new AddSpecC2SPacket(new Pair<>(this.playerSpecialization, true)));
                Minecraft.getInstance().popGuiLayer();
            } else {
                Minecraft.getInstance().setScreen(this);
            }
        }, this.playerSpecialization.getTranslation(), CHOOSE_CONFIRM));
    }
}
