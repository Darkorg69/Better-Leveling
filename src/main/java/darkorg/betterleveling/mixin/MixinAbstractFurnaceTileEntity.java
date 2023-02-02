package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.SkillRegistry;
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
            this.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(capability -> {
                if (capability.hasOwner()) {
                    PlayerEntity player = serverLevel.getPlayerByUUID(capability.getUUID());
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                            if (playerCapability.hasUnlocked(serverPlayer, SkillRegistry.COOKING_SPEED)) {
                                int currentLevel = playerCapability.getLevel(serverPlayer, SkillRegistry.COOKING_SPEED);
                                if (currentLevel > 0) {
                                    pCallbackInfoReturnable.setReturnValue(Math.toIntExact(Math.round(serverLevel.getRecipeManager().getRecipeFor(this.recipeType, (AbstractFurnaceTileEntity) (Object) this, serverLevel).map(AbstractCookingRecipe::getCookingTime).orElse(200) * SkillUtil.getDecreaseModifier(SkillRegistry.COOKING_SPEED, currentLevel))));
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}