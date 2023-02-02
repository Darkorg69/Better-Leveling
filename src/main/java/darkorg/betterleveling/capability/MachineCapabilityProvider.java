package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IMachineCapability;
import darkorg.betterleveling.impl.MachineCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MachineCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IMachineCapability.class)
    public static Capability<IMachineCapability> MACHINE_CAP = null;
    private IMachineCapability instance;
    private final LazyOptional<IMachineCapability> optional = LazyOptional.of(this::getCapability);

    private IMachineCapability getCapability() {
        return this.instance == null ? this.instance = new MachineCapability() : this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> pCapability, Direction pSide) {
        return MACHINE_CAP.orEmpty(pCapability, optional);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getCapability().getNBTData();
    }

    @Override
    public void deserializeNBT(CompoundNBT pData) {
        getCapability().setNBTData(pData);
    }
}
