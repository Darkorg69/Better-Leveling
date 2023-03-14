package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.BlockEntityCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.SkillUtil;
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
    private static void getModifiedCookTime(Level pLevel, RecipeType<? extends AbstractCookingRecipe> pRecipeType, Container pContainer, CallbackInfoReturnable<Integer> pCallbackInfoReturnable) {
        if (pLevel instanceof ServerLevel serverLevel && pContainer instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
            abstractFurnaceBlockEntity.getCapability(BlockEntityCapabilityProvider.BLOCK_ENTITY_CAP).ifPresent(blockEntityCapability -> {
                if (blockEntityCapability.hasOwner() && blockEntityCapability.getOwner(serverLevel) instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        Skill skill = Skills.COOKING_SPEED.get();
                        if (SkillUtil.hasUnlocked(playerCapability, serverPlayer, skill)) {
                            int currentLevel = playerCapability.getLevel(serverPlayer, skill);
                            if (currentLevel > 0) {
                                int originalCookTime = pLevel.getRecipeManager().getRecipeFor(pRecipeType, pContainer, pLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200);
                                double currentBonus = 1.0D - skill.getCurrentBonus(currentLevel);
                                int modifiedCookTime = Math.toIntExact(Math.round(originalCookTime * currentBonus));
                                pCallbackInfoReturnable.setReturnValue(Math.max(1, modifiedCookTime));
                            }
                        }
                    });
                }
            });
        }
    }
}