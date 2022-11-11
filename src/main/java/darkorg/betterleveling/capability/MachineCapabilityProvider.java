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

public class MachineCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<IMachineCapability> MACHINE_CAP = CapabilityManager.get(new CapabilityToken<>() {
    });

    private IMachineCapability instance;
    private final LazyOptional<IMachineCapability> optional = LazyOptional.of(this::getCapability);

    private IMachineCapability getCapability() {
        return this.instance == null ? this.instance = new MachineCapability() : this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return MACHINE_CAP.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getCapability().getNBTData();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getCapability().setNBTData(nbt);
    }
}
