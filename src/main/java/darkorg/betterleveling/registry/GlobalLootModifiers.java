package darkorg.betterleveling.registry;

import com.mojang.serialization.Codec;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawDebrisLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GlobalLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BetterLeveling.MOD_ID);
    public static final RegistryObject<Codec<RawDebrisLootModifier>> RAW_DEBRIS_LOOT_MODIFIER = GLM.register("raw_debris_loot", RawDebrisLootModifier.CODEC);

    public static void init() {
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
