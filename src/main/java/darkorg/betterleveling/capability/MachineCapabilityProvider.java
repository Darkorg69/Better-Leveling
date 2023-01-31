package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IMachineCapability;
import darkorg.betterleveling.impl.MachineCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class MachineCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<IMachineCapability> MACHINE_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private IMachineCapability instance;

    private final LazyOptional<IMachineCapability> optional = LazyOptional.of(this::getCapability);

    private IMachineCapability getCapability() {
        return this.instance == null ? this.instance = new MachineCapability() : this.instance;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> pCapability, Direction pSide) {
        return MACHINE_CAP.orEmpty(pCapability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getCapability().getNBTData();
    }

    @Override
    public void deserializeNBT(CompoundTag pCompoundTag) {
        getCapability().setNBTData(pCompoundTag);
    }
}
