package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CraftingEvents {
    private static int tick;

    @SubscribeEvent
    public static void onGreenThumb(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.player instanceof ServerPlayerEntity) {
            if (tick < 100) tick++;
            else {
                tick = 0;
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(serverPlayer, SkillRegistry.GREEN_THUMB)) {
                        int level = capability.getLevel(serverPlayer, SkillRegistry.GREEN_THUMB);
                        if (level > 0) {
                            float chance = level * 0.025F;
                            ServerWorld serverLevel = serverPlayer.getLevel();
                            if (serverLevel.random.nextFloat() <= chance) {
                                BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().inflate(8, 8, 8)).forEach(pos -> {
                                    BlockState state = serverLevel.getBlockState(pos);
                                    Block block = state.getBlock();
                                    if (block instanceof IPlantable) {
                                        IPlantable plant = (IPlantable) block;
                                        PlantType type = plant.getPlantType(serverLevel, pos);
                                        if (type == PlantType.CROP || type == PlantType.NETHER) {
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

    @SubscribeEvent
    public static void onHarvestProficiency(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity && !player.isCreative()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            BlockState state = event.getState();
            Block block = state.getBlock();
            if (state.is(ModTags.Blocks.CROPS) && block instanceof IGrowable) {
                IGrowable growable = (IGrowable) block;
                BlockPos pos = event.getPos();
                ServerWorld serverLevel = serverPlayer.getLevel();
                if (!growable.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.isUnlocked(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY)) {
                            int level = capability.getLevel(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY);
                            if (level > 0) {
                                List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null);
                                drops.forEach(stack -> {
                                    if (stack.getItem().is(ModTags.Items.CROPS)) {
                                        stack.setCount(Math.round(stack.getCount() * RandomValueRange.between(0, level * 0.5F).getFloat(serverLevel.random)));
                                        Block.popResource(serverLevel, pos, stack);
                                    }
                                });
                            }
                        }
                    });
                }
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
                        drops.forEach(entity -> {
                            ItemStack stack = entity.getItem();
                            if (stack.getItem().is(ModTags.Items.ANIMAL_SKIN)) {
                                int count = stack.getCount();
                                stack.setCount(count + Math.round(count * RandomValueRange.between(0.0F, level * 0.5F).getFloat(random)));
                            }
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (directEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) directEntity;
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(serverPlayer, SkillRegistry.MEAT_GATHERING)) {
                    int level = capability.getLevel(serverPlayer, SkillRegistry.MEAT_GATHERING);
                    if (level > 0) {
                        Random random = new Random();
                        Collection<ItemEntity> drops = event.getDrops();
                        drops.forEach(entity -> {
                            ItemStack stack = entity.getItem();
                            if (stack.getItem().is(ModTags.Items.ANIMAL_MEAT)) {
                                int count = stack.getCount();
                                stack.setCount(count + Math.round(count * RandomValueRange.between(0.0F, level * 0.5F).getFloat(random)));
                            }
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSwimSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.SWIM_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SWIM_SPEED);
                        if (level > 0) {
                            ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, SkillRegistry.SWIM_SPEED.getName(), level * 0.1F, AttributeModifier.Operation.MULTIPLY_BASE);
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

    @SubscribeEvent
    public static void onCookingSpeed(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        if (event.getSide() == LogicalSide.SERVER && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            TileEntity blockEntity = event.getWorld().getBlockEntity(event.getPos());
            if (blockEntity instanceof AbstractFurnaceTileEntity) {
                AbstractFurnaceTileEntity tileEntity = (AbstractFurnaceTileEntity) blockEntity;
                tileEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(capability -> {
                    if (capability.hasOwner()) {
                        if (capability.isOwner(serverPlayer)) {
                            if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                capability.removeOwner();
                                serverPlayer.displayClientMessage(ModComponents.UNREGISTER, true);
                                event.setCanceled(true);
                            }
                        } else {
                            if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                                serverPlayer.displayClientMessage(ModComponents.NOT_OWNED, true);
                                event.setCanceled(true);
                            } else {
                                if (ServerConfig.LOCK_BOUND_MACHINES.get()) {
                                    serverPlayer.displayClientMessage(ModComponents.NO_ACCESS, true);
                                    event.setCanceled(true);
                                }
                            }
                        }
                    } else {
                        if (serverPlayer.isCrouching() && event.getItemStack().isEmpty()) {
                            capability.setOwner(serverPlayer);
                            serverPlayer.displayClientMessage(ModComponents.REGISTER, true);
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }
    }
}
