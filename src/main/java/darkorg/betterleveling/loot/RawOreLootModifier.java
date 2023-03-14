package darkorg.betterleveling.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class RawOreLootModifier extends LootModifier {
    private final Item generated;

    public RawOreLootModifier(LootItemCondition[] conditionsIn, Item pGenerated) {
        super(conditionsIn);
        this.generated = pGenerated;
    }


    @Override
    protected List<ItemStack> doApply(List<ItemStack> pGeneratedLoot, LootContext pContext) {
        ItemStack tool = pContext.getParamOrNull(LootContextParams.TOOL);

        if (tool != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
            pGeneratedLoot.clear();

            ItemStack stack = new ItemStack(this.generated);
            ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build().apply(stack, pContext);

            pGeneratedLoot.add(stack);
        }
        return pGeneratedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RawOreLootModifier> {
        @Override
        public RawOreLootModifier read(ResourceLocation location, JsonObject pJsonObject, LootItemCondition[] pLootItemConditions) {
            return new RawOreLootModifier(pLootItemConditions, ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(pJsonObject, "generated"))));
        }

        @Override
        public JsonObject write(RawOreLootModifier instance) {
            JsonObject jsonObject = this.makeConditions(instance.conditions);

            ResourceLocation key = ForgeRegistries.ITEMS.getKey(instance.generated);
            if (key != null) {
                jsonObject.addProperty("generated", key.toString());
            }

            return jsonObject;
        }
    }
}
