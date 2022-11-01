package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static darkorg.betterleveling.registry.AttributeModifiers.SWIM_SPEED_MODIFIER;
import static darkorg.betterleveling.registry.SkillRegistry.GREEN_THUMB;
import static darkorg.betterleveling.registry.SkillRegistry.SWIM_SPEED;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftingEvents {
    private static int tickCount;

    @SubscribeEvent
    public static void onGreenThumb(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            PlayerEntity player = event.player;
            if (event.player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                World world = serverPlayer.world;
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    tickCount++;
                    if (tickCount >= 100) {
                        tickCount = 0;
                        serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                            if (capability.isUnlocked(serverPlayer, GREEN_THUMB)) {
                                int level = capability.getLevel(serverPlayer, GREEN_THUMB);
                                if (level > 0) {
                                    float chance = level * 0.03F;
                                    if (serverWorld.rand.nextFloat() <= chance) {
                                        BlockPos.getAllInBox(serverPlayer.getBoundingBox().expand(8.0F, 8.0F, 8.0F)).forEach(pos -> {
                                            BlockState state = serverWorld.getBlockState(pos);
                                            Block block = state.getBlock();
                                            if (block instanceof IPlantable) {
                                                IPlantable plant = (IPlantable) block;
                                                PlantType plantType = plant.getPlantType(serverWorld, pos);
                                                if (plantType == PlantType.CROP || plantType == PlantType.NETHER) {
                                                    state.randomTick(serverWorld, pos, serverWorld.rand);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSwimmingSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SWIM_SPEED)) {
                        int level = capability.getLevel(player, SWIM_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.1F;
                            ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(SWIM_SPEED_MODIFIER, SWIM_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSwimming()) {
                                    if (attribute.getModifier(SWIM_SPEED_MODIFIER) == null) {
                                        attribute.applyNonPersistentModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(SWIM_SPEED_MODIFIER) != null) {
                                        attribute.removeModifier(attributeModifier);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
