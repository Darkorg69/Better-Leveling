package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawOreLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GlobalLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, BetterLeveling.MOD_ID);
    public static final RegistryObject<RawOreLootModifier.Serializer> RAW_ORE_LOOT = GLM.register("raw_ore_loot", RawOreLootModifier.Serializer::new);

    public static void init() {
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
