package darkorg.betterleveling.mixin;

import com.mojang.authlib.GameProfile;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayerEntity extends Player {
    public MixinServerPlayerEntity(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
        super(pLevel, pPos, pYRot, pGameProfile);
    }

    @Inject(at = @At("TAIL"), method = "setExperiencePoints")
    public void setExperiencePoints(int pLevels, CallbackInfo pCallbackInfo) {
        this.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.updateAvailableExperience((ServerPlayer) (Object) this));
    }

    @Inject(at = @At("TAIL"), method = "giveExperiencePoints")
    public void giveExperiencePoints(int pXpPoints, CallbackInfo pCallbackInfo) {
        this.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.updateAvailableExperience((ServerPlayer) (Object) this));
    }

    @Inject(at = @At("TAIL"), method = "setExperienceLevels")
    public void setExperienceLevels(int pLevels, CallbackInfo pCallbackInfo) {
        this.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.updateAvailableExperience((ServerPlayer) (Object) this));
    }

    @Inject(at = @At("TAIL"), method = "giveExperienceLevels")
    public void giveExperienceLevels(int pLevels, CallbackInfo pCallbackInfo) {
        this.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> pCapability.updateAvailableExperience((ServerPlayer) (Object) this));
    }
}