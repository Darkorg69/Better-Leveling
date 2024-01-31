package darkorg.betterleveling.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import darkorg.betterleveling.gui.widget.button.ChooseSpecializationButton;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.specialization.RequestSpecializationsScreenC2SPacket;
import darkorg.betterleveling.network.packets.specialization.UnlockSpecializationC2SPacket;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChooseSpecializationScreen extends Screen {
    private static final ImmutableList<Specialization> SPECIALIZATIONS = Specializations.getAll();
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos;
    private int topPos;
    private Specialization specialization;

    public ChooseSpecializationScreen() {
        super(ModComponents.CHOOSE_SPEC_TITLE);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.specialization = SPECIALIZATIONS.get(0);

        ChooseSpecializationButton chooseSpecializationButton = new ChooseSpecializationButton(this.width / 2, this.height / 2 - 32, this.specialization, SPECIALIZATIONS, this::onValueChange);
        addRenderableWidget(chooseSpecializationButton);

        ExtendedButton selectButton = new ExtendedButton((this.width - 75) / 2, this.topPos + 116, 75, 25, ModComponents.SELECT_BUTTON, this::onPress);
        addRenderableWidget(selectButton);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, this.topPos - 10, 16777215);
        RenderUtil.setShaderTexture();
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void onValueChange(Specialization pSpecialization) {
        this.specialization = pSpecialization;
    }

    private void onPress(Button pSelectButton) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), ModComponents.CHOOSE_CONFIRM));
    }

    private void onCallback(boolean pConfirm) {
        if (pConfirm) {
            NetworkHandler.sendToServer(new UnlockSpecializationC2SPacket(this.specialization));
            NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.specialization));
        } else {
            Minecraft.getInstance().setScreen(this);
        }
    }
}
