package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class MixinAbstractFurnaceBlockEntity {
    @Inject(at = @At("HEAD"), method = "getTotalCookTime", cancellable = true)
    private static void getModifiedCookTime(Level pLevel, RecipeType<? extends AbstractCookingRecipe> pRecipeType, Container pContainer, CallbackInfoReturnable<Integer> cir) {
        if (pLevel instanceof ServerLevel serverLevel && pContainer instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
            abstractFurnaceBlockEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(machineCapability -> {
                if (machineCapability.hasOwner() && serverLevel.getPlayerByUUID(machineCapability.getOwnerId()) instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        if (playerCapability.isUnlocked(serverPlayer, SkillRegistry.COOKING_SPEED)) {
                            int skillLevel = playerCapability.getLevel(serverPlayer, SkillRegistry.COOKING_SPEED);
                            if (skillLevel > 0) {
                                int original = pLevel.getRecipeManager().getRecipeFor(pRecipeType, pContainer, pLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200);
                                float modifier = 1.0F - (skillLevel * 0.09F);
                                cir.setReturnValue(Math.round(original * modifier));
                            }
                        }
                    });
                }
            });
        }
    }
}