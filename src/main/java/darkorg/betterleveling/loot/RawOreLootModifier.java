package darkorg.betterleveling.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RawOreLootModifier extends LootModifier {
    public static final Supplier<Codec<RawOreLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance -> codecStart(instance).and(ForgeRegistries.ITEMS.getCodec().fieldOf("generated").forGetter(t -> t.generated)).apply(instance, RawOreLootModifier::new)));

    private final Item generated;

    public RawOreLootModifier(LootItemCondition[] conditionsIn, Item pGenerated) {
        super(conditionsIn);
        this.generated = pGenerated;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> pGeneratedLoot, LootContext pLootContext) {
        ItemStack tool = pLootContext.getParamOrNull(LootContextParams.TOOL);

        if (tool != null && tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
            BlockState pBlockState = pLootContext.getParamOrNull(LootContextParams.BLOCK_STATE);

            if (pBlockState != null) {
                for (ItemStack pItemStack : pGeneratedLoot) {
                    if (pItemStack.is(pBlockState.getBlock().asItem())) {
                        pItemStack.setCount(0);
                    }
                }
                ItemStack generated = new ItemStack(this.generated);
                ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build().apply(generated, pLootContext);
                pGeneratedLoot.add(generated);
            }
        }

        return pGeneratedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
