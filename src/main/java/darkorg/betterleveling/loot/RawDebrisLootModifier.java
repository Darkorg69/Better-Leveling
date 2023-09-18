package darkorg.betterleveling.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import darkorg.betterleveling.registry.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RawDebrisLootModifier extends LootModifier {
    public static final Supplier<Codec<RawDebrisLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, RawDebrisLootModifier::new)));

    public RawDebrisLootModifier(LootItemCondition[] pConditions) {
        super(pConditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> pGeneratedLoot, @NotNull LootContext pContext) {
        ItemStack itemStack = pContext.getParamOrNull(LootContextParams.TOOL);

        if (itemStack != null && itemStack.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
            pGeneratedLoot.clear();

            ItemStack generated = new ItemStack(ModItems.RAW_DEBRIS.get());
            ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build().apply(generated, pContext);

            pGeneratedLoot.add(generated);
        }
        return pGeneratedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
