package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.BlockEntityCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceTileEntity.class)
abstract class MixinAbstractFurnaceTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    @Shadow
    @Final
    protected IRecipeType<? extends AbstractCookingRecipe> recipeType;

    protected MixinAbstractFurnaceTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Inject(at = @At("HEAD"), method = "getTotalCookTime", cancellable = true)
    protected void getModifiedCookTime(CallbackInfoReturnable<Integer> pCallbackInfoReturnable) {
        if (this.level instanceof ServerWorld) {
            ServerWorld serverLevel = (ServerWorld) this.level;
            this.getCapability(BlockEntityCapabilityProvider.BLOCK_ENTITY_CAP).ifPresent(blockEntityCapability -> {
                if (blockEntityCapability.hasOwner()) {
                    PlayerEntity player = blockEntityCapability.getOwner(serverLevel);
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                            if (SkillUtil.hasUnlocked(playerCapability, serverPlayer, Skills.COOKING_SPEED.get())) {
                                int currentLevel = playerCapability.getLevel(serverPlayer, Skills.COOKING_SPEED.get());
                                if (currentLevel > 0) {
                                    pCallbackInfoReturnable.setReturnValue(Math.max(1, Math.toIntExact(Math.round(serverLevel.getRecipeManager().getRecipeFor(this.recipeType, (AbstractFurnaceTileEntity) (Object) this, serverLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200) * (1.0D - Skills.COOKING_SPEED.get().getCurrentBonus(currentLevel))))));
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}