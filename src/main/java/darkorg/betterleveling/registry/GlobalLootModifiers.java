package darkorg.betterleveling.registry;

import com.mojang.serialization.Codec;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.loot.RawOreLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class GlobalLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BetterLeveling.MOD_ID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> RAW_ORE_LOOT = GLM.register("raw_ore_loot", RawOreLootModifier.CODEC);

    public static void init() {
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
