package darkorg.betterleveling.util;

import com.mojang.blaze3d.systems.RenderSystem;
import darkorg.betterleveling.BetterLeveling;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtil {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(BetterLeveling.MOD_ID, "textures/gui/background.png");

    public static void setShaderTexture() {
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void setShaderTextureButton() {
        setShaderTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }
}
