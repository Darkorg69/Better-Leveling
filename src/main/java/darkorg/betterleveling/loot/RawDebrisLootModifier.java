package darkorg.betterleveling.loot;

import com.google.gson.JsonObject;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RawDebrisLootModifier extends LootModifier {
    public RawDebrisLootModifier(LootItemCondition[] pConditions) {
        super(pConditions);
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> pGeneratedLoot, LootContext pContext) {
        ItemStack itemStack = pContext.getParamOrNull(LootContextParams.TOOL);

        if (itemStack != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) <= 0) {
            pGeneratedLoot.clear();

            ItemStack generated = new ItemStack(ModItems.RAW_DEBRIS.get());
            ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build().apply(generated, pContext);

            pGeneratedLoot.add(generated);
        }
        return pGeneratedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RawDebrisLootModifier> {
        @Override
        public JsonObject write(RawDebrisLootModifier instance) {
            return this.makeConditions(instance.conditions);
        }

        @Override
        public RawDebrisLootModifier read(ResourceLocation pResourceLocation, JsonObject pObject, LootItemCondition[] pConditions) {
            return new RawDebrisLootModifier(pConditions);
        }
    }
}
