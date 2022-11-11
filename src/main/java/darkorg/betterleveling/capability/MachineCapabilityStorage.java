package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class MachineCapabilityStorage implements Capability.IStorage<IMachineCapability> {
    @Override
    public INBT writeNBT(Capability<IMachineCapability> capability, IMachineCapability instance, Direction side) {
        return instance.getNBTData();
    }

    @Override
    public void readNBT(Capability<IMachineCapability> capability, IMachineCapability instance, Direction side, INBT nbt) {
        instance.setNBTData((CompoundNBT) nbt);
    }
}
