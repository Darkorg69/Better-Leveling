package darkorg.betterleveling.mixin;

import com.mojang.authlib.GameProfile;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity implements IContainerListener {
    public MixinServerPlayerEntity(World pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
        super(pLevel, pPos, pYRot, pGameProfile);
    }

    @Inject(at = @At("TAIL"), method = "setExperiencePoints")
    public void setExperiencePoints(int pLevels, CallbackInfo pCallbackInfo) {
        updateAvailableExperience();
    }

    @Inject(at = @At("TAIL"), method = "giveExperiencePoints")
    public void giveExperiencePoints(int pXpPoints, CallbackInfo pCallbackInfo) {
        updateAvailableExperience();
    }

    @Inject(at = @At("TAIL"), method = "setExperienceLevels")
    public void setExperienceLevels(int pLevels, CallbackInfo pCallbackInfo) {
        updateAvailableExperience();
    }

    @Inject(at = @At("TAIL"), method = "giveExperienceLevels")
    public void giveExperienceLevels(int pLevels, CallbackInfo pCallbackInfo) {
        updateAvailableExperience();
    }

    private void updateAvailableExperience() {
        this.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
            pCapability.updateAvailableExperience((ServerPlayerEntity) (Object) this);
        });
    }
}