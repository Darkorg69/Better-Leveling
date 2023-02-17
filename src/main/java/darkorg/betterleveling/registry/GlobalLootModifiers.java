package darkorg.betterleveling.registry;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawDebrisLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GlobalLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, BetterLeveling.MOD_ID);
    public static final RegistryObject<RawDebrisLootModifier.Serializer> RAW_DEBRIS_LOOT = GLM.register("raw_debris_loot", RawDebrisLootModifier.Serializer::new);

    public static void init() {
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
