package darkorg.betterleveling.event;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.MachineCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.config.ServerConfig;
import darkorg.betterleveling.network.chat.ModTranslatableContents;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.SkillRegistry;
import darkorg.betterleveling.registry.SpecRegistry;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.StackUtil;
import darkorg.betterleveling.util.StateUtil;
import darkorg.betterleveling.util.TreasureUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID)
public class CraftingEvents {
    private static int tickCount;

    @SubscribeEvent
    public static void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.getUnlocked(serverPlayer, SpecRegistry.CRAFTING)) {
                    List<ItemStack> ingredients = StackUtil.getIngredients(event.getInventory());
                    if (ingredients.size() > 1) {
                        serverPlayer.giveExperiencePoints(Math.toIntExact(Math.round(ingredients.stream().mapToInt(ItemStack::getCount).sum() * new Random().nextDouble(ServerConfig.COMBAT_XP_BONUS.get()))));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onGreenThumb(PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.END && event.player instanceof ServerPlayer serverPlayer) {
            tickCount++;
            if (tickCount > ServerConfig.GREEN_THUMB_TICK_RATE.get()) {
                tickCount = 0;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(serverPlayer, SkillRegistry.GREEN_THUMB)) {
                        int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.GREEN_THUMB);
                        if (currentLevel > 0) {
                            ServerLevel serverLevel = serverPlayer.getLevel();
                            if (serverLevel.random.nextDouble() <= SkillUtil.getCurrentChance(SkillRegistry.GREEN_THUMB, currentLevel)) {
                                BlockPos.betweenClosedStream(serverPlayer.getBoundingBox().inflate(16, 16, 16)).forEach(pos -> {
                                    BlockState state = serverLevel.getBlockState(pos);
                                    if (StateUtil.isCropBlock(state)) {
                                        state.randomTick(serverLevel, pos, serverLevel.random);
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
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
            BlockState state = event.getState();
            if (StateUtil.isCropBlock(state) && state.getBlock() instanceof BonemealableBlock bonemealableBlock) {
                BlockPos pos = event.getPos();
                ServerLevel serverLevel = serverPlayer.getLevel();
                if (!bonemealableBlock.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.hasUnlocked(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY)) {
                            int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.HARVEST_PROFICIENCY);
                            if (currentLevel > 0) {
                                Random random = new Random();
                                if (random.nextDouble() <= SkillUtil.getCurrentChance(SkillRegistry.HARVEST_PROFICIENCY, currentLevel)) {
                                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, serverPlayer, serverPlayer.getMainHandItem());
                                    drops.forEach(stack -> {
                                        if (StackUtil.isCropOrSeed(stack)) {
                                            stack.setCount(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.HARVEST_PROFICIENCY_POTENTIAL_LOOT_BOUND.get(), random));
                                        }
                                        Block.popResource(serverLevel, pos, stack);
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
    public static void onSkinning(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, SkillRegistry.SKINNING)) {
                    int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.SKINNING);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= SkillUtil.getCurrentChance(SkillRegistry.SKINNING, currentLevel)) {
                            event.getDrops().stream().map(ItemEntity::getItem).filter(StackUtil::isSkin).forEach(stack -> stack.grow(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.MEAT_GATHERING_POTENTIAL_LOOT_BOUND.get(), random)));
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMeatGathering(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.hasUnlocked(serverPlayer, SkillRegistry.MEAT_GATHERING)) {
                    int currentLevel = capability.getLevel(serverPlayer, SkillRegistry.MEAT_GATHERING);
                    if (currentLevel > 0) {
                        Random random = new Random();
                        if (random.nextDouble() <= SkillUtil.getCurrentChance(SkillRegistry.MEAT_GATHERING, currentLevel)) {
                            event.getDrops().stream().map(ItemEntity::getItem).filter(StackUtil::isMeat).forEach(stack -> stack.grow(TreasureUtil.getPotentialLoot(stack.getCount(), ServerConfig.MEAT_GATHERING_POTENTIAL_LOOT_BOUND.get(), random)));
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSwimSpeed(PlayerTickEvent event) {
        if (event.phase == Phase.START) {
            Player player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.hasUnlocked(player, SkillRegistry.SWIM_SPEED)) {
                        int currentLevel = capability.getLevel(player, SkillRegistry.SWIM_SPEED);
                        if (currentLevel > 0) {
                            AttributeInstance attribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SWIM_SPEED_MODIFIER, SkillRegistry.SWIM_SPEED.getName(), SkillUtil.getCurrentBonus(SkillRegistry.SWIM_SPEED, currentLevel), AttributeModifier.Operation.MULTIPLY_BASE);
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
        if (event.getSide() == LogicalSide.SERVER && event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (event.getLevel().getBlockEntity(event.getPos()) instanceof AbstractFurnaceBlockEntity blockEntity) {
                blockEntity.getCapability(MachineCapabilityProvider.MACHINE_CAP).ifPresent(capability -> {
                    ItemStack stack = event.getItemStack();
                    if (capability.hasOwner()) {
                        if (capability.isOwner(serverPlayer)) {
                            if (serverPlayer.isCrouching() && stack.isEmpty()) {
                                capability.removeOwner();
                                serverPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.UNREGISTER), true);
                                event.setCanceled(true);
                            }
                        } else {
                            if (serverPlayer.isCrouching() && stack.isEmpty()) {
                                serverPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.NOT_OWNED), true);
                                event.setCanceled(true);
                            } else {
                                if (ServerConfig.LOCK_BOUND_MACHINES.get()) {
                                    serverPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.NO_ACCESS), true);
                                    event.setCanceled(true);
                                }
                            }
                        }
                    } else {
                        if (serverPlayer.isCrouching() && stack.isEmpty()) {
                            capability.setOwner(serverPlayer);
                            serverPlayer.displayClientMessage(MutableComponent.create(ModTranslatableContents.REGISTER), true);
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }
    }
}
