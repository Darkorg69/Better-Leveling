package darkorg.betterleveling.loot;

import com.google.gson.JsonObject;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.util.StackUtil;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class RawOreLootModifier extends LootModifier {
    private final Item generated;

    public RawOreLootModifier(ILootCondition[] pLootConditions, Item pGenerated) {
        super(pLootConditions);
        this.generated = pGenerated;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> pGeneratedLoot, LootContext pContext) {
        if (ModConfig.GAMEPLAY.applyRawOreLootModifier.get()) {
            ItemStack tool = pContext.getParamOrNull(LootParameters.TOOL);
            if (!StackUtil.isSilktouch(tool)) {
                pGeneratedLoot.clear();

                ItemStack stack = new ItemStack(this.generated);
                ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build().apply(stack, pContext);

                pGeneratedLoot.add(stack);
            }
        }
        return pGeneratedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RawOreLootModifier> {
        @Override
        public JsonObject write(RawOreLootModifier pInstance) {
            JsonObject jsonObject = this.makeConditions(pInstance.conditions);

            ResourceLocation key = ForgeRegistries.ITEMS.getKey(pInstance.generated);
            if (key != null) {
                jsonObject.addProperty("generated", key.toString());
            }

            return jsonObject;
        }

        @Override
        public RawOreLootModifier read(ResourceLocation pResourceLocation, JsonObject pJsonObject, ILootCondition[] pConditions) {
            return new RawOreLootModifier(pConditions, ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(pJsonObject, "generated"))));
        }
    }
}
