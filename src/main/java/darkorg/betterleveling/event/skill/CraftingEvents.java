package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftingEvents {
    private static int tickCount;

    @SubscribeEvent
    public static void onGreenThumb(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            PlayerEntity player = event.player;
            if (player instanceof ServerPlayerEntity) {
                tickCount++;
                if (tickCount >= 100) {
                    tickCount = 0;
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.isUnlocked(serverPlayer, SkillRegistry.GREEN_THUMB)) {
                            int level = capability.getLevel(serverPlayer, SkillRegistry.GREEN_THUMB);
                            if (level > 0) {
                                float chance = level * 0.03F;
                                ServerWorld serverLevel = serverPlayer.getLevel();
                                if (serverLevel.random.nextFloat() <= chance) {
                                    BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().expandTowards(8.0F, 8.0F, 8.0F)).forEach(pos -> {
                                        BlockState state = serverLevel.getBlockState(pos);
                                        Block block = state.getBlock();
                                        if (block instanceof IPlantable) {
                                            IPlantable plant = (IPlantable) block;
                                            PlantType plantType = plant.getPlantType(serverLevel, pos);
                                            if (plantType == PlantType.CROP || plantType == PlantType.NETHER) {
                                                state.randomTick(serverLevel, pos, serverLevel.random);
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

    @SubscribeEvent
    public static void onHarvestEfficiency(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.isCreative()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY)) {
                        int level = capability.getLevel(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY);
                        if (level > 0) {
                            BlockState state = event.getState();
                            if (state.getBlock().is(BlockTags.CROPS)) {
                                Random random = new Random();
                                ServerWorld serverLevel = serverPlayer.getLevel();
                                List<ItemStack> drops = Block.getDrops(state, serverLevel, event.getPos(), null);
                                drops.forEach(itemStack -> {
                                    if (itemStack.getItem().is(Tags.Items.CROPS)) {
                                        itemStack.setCount(RandomValueRange.between(0, level).getInt(random));
                                        Block.popResource(serverLevel, event.getPos(), itemStack);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onSkinning(LivingDropsEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (directEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) directEntity;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.SKINNING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.SKINNING);
                    if (level > 0) {
                        Random random = new Random();
                        Collection<ItemEntity> drops = event.getDrops();
                        drops.forEach(itemEntity -> {
                            ItemStack itemStack = itemEntity.getItem();
                            if (itemStack.getItem().is(ModTags.Items.SKINS)) {
                                itemStack.setCount(itemStack.getCount() + RandomValueRange.between(0, level).getInt(random));
                            }
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        Entity trueSource = event.getSource().getDirectEntity();
        if (trueSource instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) trueSource;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.MEAT_GATHERING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.MEAT_GATHERING);
                    if (level > 0) {
                        Random random = new Random();
                        Collection<ItemEntity> drops = event.getDrops();
                        drops.forEach(itemEntity -> {
                            ItemStack itemStack = itemEntity.getItem();
                            if (itemStack.getItem().is(ModTags.Items.MEATS)) {
                                itemStack.setCount(itemStack.getCount() + RandomValueRange.between(0, level).getInt(random));
                            }
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSwimmingSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.SWIM_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SWIM_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.1F;
                            ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, SkillRegistry.SWIM_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSwimming()) {
                                    if (attribute.getModifier(AttributeModifiers.SWIM_SPEED_MODIFIER) == null) {
                                        attribute.addTransientModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(AttributeModifiers.SWIM_SPEED_MODIFIER) != null) {
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
