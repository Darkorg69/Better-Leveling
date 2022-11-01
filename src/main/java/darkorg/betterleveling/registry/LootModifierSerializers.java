package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.data.loot.BlockLootModifier;
import darkorg.betterleveling.data.loot.EntityLootModifier;
import darkorg.betterleveling.data.loot.TreasureLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootModifierSerializers {
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, BetterLeveling.MOD_ID);

    public static final RegistryObject<BlockLootModifier.Serializer> BLOCK_LOOT = LOOT_MODIFIER_SERIALIZERS.register("block_loot", BlockLootModifier.Serializer::new);
    public static final RegistryObject<EntityLootModifier.Serializer> ENTITY_LOOT = LOOT_MODIFIER_SERIALIZERS.register("entity_loot", EntityLootModifier.Serializer::new);
    public static final RegistryObject<TreasureLootModifier.Serializer> TREASURE_LOOT = LOOT_MODIFIER_SERIALIZERS.register("treasure_loot", TreasureLootModifier.Serializer::new);

    public static void init() {
        LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
