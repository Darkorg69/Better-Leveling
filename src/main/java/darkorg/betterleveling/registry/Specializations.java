package darkorg.betterleveling.registry;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.config.ModConfig;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.impl.specialization.SpecializationProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Specializations {
    private static final DeferredRegister<Specialization> SPECIALIZATIONS = DeferredRegister.create(new ResourceLocation(BetterLeveling.MOD_ID, "specializations"), BetterLeveling.MOD_ID);

    public static final RegistryObject<Specialization> COMBAT = register("combat", () -> new Specialization(new SpecializationProperties(Items.IRON_SWORD, ModConfig.SPECIALIZATIONS.combatCost)));
    public static final RegistryObject<Specialization> CRAFTING = register("crafting", () -> new Specialization(new SpecializationProperties(Items.CRAFTING_TABLE, ModConfig.SPECIALIZATIONS.craftingCost)));
    public static final RegistryObject<Specialization> MINING = register("mining", () -> new Specialization(new SpecializationProperties(Items.IRON_PICKAXE, ModConfig.SPECIALIZATIONS.miningCost)));

    private static final Supplier<IForgeRegistry<Specialization>> REGISTRY = SPECIALIZATIONS.makeRegistry(RegistryBuilder::new);

    public static void init() {
        SPECIALIZATIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static IForgeRegistry<Specialization> getRegistry() {
        return REGISTRY.get();
    }

    private static <T extends Specialization> RegistryObject<T> register(String pName, Supplier<T> pSpecialization) {
        return SPECIALIZATIONS.register(pName, pSpecialization);
    }

    public static ImmutableList<Specialization> getAll() {
        return ImmutableList.copyOf(getRegistry().getValues());
    }

    public static Specialization getFrom(String pName) {
        return getFrom(BetterLeveling.MOD_ID, pName);
    }

    public static Specialization getFrom(String pModId, String pName) {
        return getFrom(new ResourceLocation(pModId, pName));
    }

    public static Specialization getFrom(ResourceLocation pResourceLocation) {
        return getRegistry().getValue(pResourceLocation);
    }

    public static ImmutableList<String> getAllNames() {
        List<String> names = new ArrayList<>();

        for (Specialization specialization : getAll()) {
            String name = specialization.getName();
            names.add(name);
        }

        return ImmutableList.copyOf(names);
    }
}
