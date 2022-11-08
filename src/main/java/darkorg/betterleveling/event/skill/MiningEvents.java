package darkorg.betterleveling.event.skill;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.registry.AttributeModifiers;
import darkorg.betterleveling.registry.ModTags;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterLeveling.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MiningEvents {
    @SubscribeEvent
    public static void onStoneCutting(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (event.getState().getMaterial() == Material.STONE) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.STONECUTTING)) {
                        int level = capability.getLevel(player, SkillRegistry.STONECUTTING);
                        float modifier = 1 + (level * 0.1F);
                        if (level > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onProspecting(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.isCreative()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(serverPlayer, SkillRegistry.PROSPECTING)) {
                        int level = capability.getLevel(serverPlayer, SkillRegistry.PROSPECTING);
                        if (level > 0) {
                            BlockState state = event.getState();
                            if (state.getBlock().is(ModTags.Blocks.ORES)) {
                                Random random = new Random();
                                ServerWorld serverLevel = serverPlayer.getLevel();
                                List<ItemStack> drops = Block.getDrops(state, serverLevel, event.getPos(), null);
                                drops.forEach(itemStack -> {
                                    if (itemStack.getItem().is(ModTags.Items.ORES)) {
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
    public static void onWoodcutting(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (event.getState().getMaterial() == Material.WOOD || event.getState().getMaterial() == Material.NETHER_WOOD) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.WOODCUTTING)) {
                        int level = capability.getLevel(player, SkillRegistry.WOODCUTTING);
                        float modifier = 1 + (level * 0.1F);
                        if (level > 0) {
                            event.setNewSpeed(event.getOriginalSpeed() * modifier);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onTreasureHunting(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.isCreative()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(serverPlayer, SkillRegistry.TREASURE_HUNTING)) {
                        int level = capability.getLevel(serverPlayer, SkillRegistry.TREASURE_HUNTING);
                        if (level > 0) {
                            if (event.getState().getBlock().is(ModTags.Blocks.TREASURE_BLOCKS)) {
                                Random random = new Random();
                                BlockPos pos = event.getPos();
                                ServerWorld serverWorld = serverPlayer.getLevel();
                                float roll = random.nextFloat();
                                if (roll <= level * 0.001F) {
                                    spawnTreasure(serverWorld, pos, random, new ItemStack(ModTags.Items.RARE_LOOT.getRandomElement(random)));
                                } else {
                                    if (roll <= level * 0.002F) {
                                        spawnTreasure(serverWorld, pos, random, new ItemStack(ModTags.Items.UNCOMMON_LOOT.getRandomElement(random)));
                                    } else {
                                        if (roll <= level * 0.005F) {
                                            spawnTreasure(serverWorld, pos, random, new ItemStack(ModTags.Items.COMMON_LOOT.getRandomElement(random)));
                                        }
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
    public static void onSoftLanding(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                if (capability.isUnlocked(player, SkillRegistry.SOFT_LANDING)) {
                    int level = capability.getLevel(player, SkillRegistry.SOFT_LANDING);
                    if (level > 0) {
                        float reduction = 1 - (level * 0.05F);
                        event.setDamageMultiplier(event.getDamageMultiplier() * reduction);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSprintSpeed(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null) {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    if (capability.isUnlocked(player, SkillRegistry.SPRINT_SPEED)) {
                        int level = capability.getLevel(player, SkillRegistry.SPRINT_SPEED);
                        if (level > 0) {
                            float modifier = level * 0.05F;
                            ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                            if (attribute != null) {
                                AttributeModifier attributeModifier = new AttributeModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER, SkillRegistry.SPRINT_SPEED.getName(), modifier, AttributeModifier.Operation.MULTIPLY_BASE);
                                if (player.isSprinting()) {
                                    if (attribute.getModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER) == null) {
                                        attribute.addTransientModifier(attributeModifier);
                                    }
                                } else {
                                    if (attribute.getModifier(AttributeModifiers.SPRINT_SPEED_MODIFIER) != null) {
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

    private static void spawnTreasure(ServerWorld pServerWorld, BlockPos pPos, Random pRandom, ItemStack pItemStack) {
        if (pItemStack.isDamageableItem()) {
            pItemStack.setDamageValue(Math.round(RandomValueRange.between(0.0F, 0.69F).getFloat(pRandom) * pItemStack.getMaxDamage()));
            EnchantmentHelper.enchantItem(pRandom, pItemStack, RandomValueRange.between(0, 30).getInt(pRandom), true);
        } else {
            pItemStack.setCount(RandomValueRange.between(1, 3).getInt(pRandom));
        }
        Block.popResource(pServerWorld, pPos, pItemStack);
    }
}
