package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.capability.TileEntityCapabilityProvider;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static darkorg.betterleveling.registry.SkillRegistry.COOKING_SPEED;

@Mixin(AbstractFurnaceTileEntity.class)
public abstract class MixinAbstractFurnaceTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    @Shadow
    @Final
    protected IRecipeType<? extends AbstractCookingRecipe> recipeType;

    protected MixinAbstractFurnaceTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Inject(at = @At("HEAD"), method = "getCookTime", cancellable = true)
    protected void getModifiedCookTime(CallbackInfoReturnable<Integer> cir) {
        World world = ((AbstractFurnaceTileEntity) (Object) this).getWorld();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            this.getCapability(TileEntityCapabilityProvider.TILE_CAP).ifPresent(capability -> {
                if (capability.hasOwner()) {
                    PlayerEntity owner = serverWorld.getPlayerByUuid(capability.getOwnerUUID());
                    if (owner instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) owner;
                        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                            if (playerCapability.isUnlocked(serverPlayer, COOKING_SPEED)) {
                                int level = playerCapability.getLevel(serverPlayer, COOKING_SPEED);
                                if (level > 0) {
                                    int originalCookTime = serverWorld.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) recipeType, (AbstractFurnaceTileEntity) (Object) this, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
                                    float cookTimeModifier = 1.0F - (level * 0.09F);
                                    float newCookTime = originalCookTime * cookTimeModifier;
                                    cir.setReturnValue(Math.round(newCookTime));
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}