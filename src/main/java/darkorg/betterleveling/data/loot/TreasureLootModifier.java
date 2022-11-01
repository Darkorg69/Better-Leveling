package darkorg.betterleveling.data.loot;

import com.google.gson.JsonObject;
import darkorg.betterleveling.api.ISkill;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.util.CapabilityUtil;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class TreasureLootModifier extends LootModifier {
    private final Item addition;
    private final ISkill playerSkill;
    private final float chance;
    private final int maxCount;

    public TreasureLootModifier(ILootCondition[] pConditions, Item pAddition, int pMaxCount, ISkill pSkill, float pChance) {
        super(pConditions);
        this.addition = pAddition;
        this.maxCount = pMaxCount;
        this.playerSkill = pSkill;
        this.chance = pChance;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.size() <= 1) {
            if (context.has(LootParameters.THIS_ENTITY)) {
                Entity entity = context.get(LootParameters.THIS_ENTITY);
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                    serverPlayerEntity.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                        if (capability.isUnlocked(serverPlayerEntity, this.playerSkill)) {
                            int level = capability.getLevel(serverPlayerEntity, this.playerSkill);
                            if (level > 0) {
                                if (RandomChance.builder(level * chance).build().test(context)) {
                                    ItemStack generated = new ItemStack(this.addition);
                                    if (generated.getMaxDamage() > 0) {
                                        SetDamage.func_215931_a(RandomValueRange.of(0F, 0.69F)).build().apply(generated, context);
                                        EnchantWithLevels.func_215895_a(RandomValueRange.of(0F, 30F)).build().apply(generated, context);
                                    } else {
                                        SetCount.builder(RandomValueRange.of(0, this.maxCount)).build().apply(generated, context);
                                        ApplyBonus.oreDrops(Enchantments.FORTUNE).build().apply(generated, context);
                                    }
                                    generatedLoot.add(generated);
                                }
                            }
                        }
                    });
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<TreasureLootModifier> {
        @Override
        public TreasureLootModifier read(ResourceLocation pName, JsonObject pObject, ILootCondition[] pConditionsIn) {
            Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(pObject, "addition")));

            ISkill skill = CapabilityUtil.getSkillFromName(JSONUtils.getString(pObject, "skill"));

            int maxCount = JSONUtils.getInt(pObject, "maxCount");

            float chance = JSONUtils.getFloat(pObject, "chance");

            return new TreasureLootModifier(pConditionsIn, addition, maxCount, skill, chance);
        }

        @Override
        public JsonObject write(TreasureLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);

            json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());

            json.addProperty("maxCount", instance.maxCount);

            json.addProperty("skill", instance.playerSkill.getName());

            json.addProperty("chance", instance.chance);

            return json;
        }
    }
}
