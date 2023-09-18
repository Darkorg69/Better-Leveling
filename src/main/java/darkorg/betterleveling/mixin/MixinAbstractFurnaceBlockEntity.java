package darkorg.betterleveling.mixin;

import darkorg.betterleveling.api.IAbstractFurnaceBlockEntity;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity implements IAbstractFurnaceBlockEntity {
    @Shadow @Final private RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;

    @Inject(at = @At("HEAD"), method = "getTotalCookTime", cancellable = true)
    private static void getModifiedCookTime(Level pLevel, AbstractFurnaceBlockEntity pAbstractFurnaceBlockEntity, CallbackInfoReturnable<Integer> pCallbackInfoReturnable) {
        if (pLevel instanceof ServerLevel serverLevel) {
            pAbstractFurnaceBlockEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(machineCapability -> {
                if (machineCapability.hasOwner() && serverLevel.getPlayerByUUID(machineCapability.getUUID()) instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        if (playerCapability.hasUnlocked(serverPlayer, SkillRegistry.COOKING_SPEED)) {
                            int currentLevel = playerCapability.getLevel(serverPlayer, SkillRegistry.COOKING_SPEED);
                            if (currentLevel > 0) {
                                pCallbackInfoReturnable.setReturnValue(Math.max(1, Math.toIntExact(Math.round(((IAbstractFurnaceBlockEntity)pAbstractFurnaceBlockEntity).bl$getQuickCheck().getRecipeFor(pAbstractFurnaceBlockEntity, serverLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200) * SkillUtil.getDecreaseModifier(SkillRegistry.COOKING_SPEED, currentLevel)))));
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> bl$getQuickCheck() {
        return this.quickCheck;
    }
}