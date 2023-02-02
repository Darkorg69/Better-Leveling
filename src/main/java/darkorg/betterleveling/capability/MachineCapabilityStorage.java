package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IMachineCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class MachineCapabilityStorage implements Capability.IStorage<IMachineCapability> {
    @Override
    public INBT writeNBT(Capability<IMachineCapability> pCapability, IMachineCapability pInstance, Direction pSide) {
        return pInstance.getNBTData();
    }

    @Override
    public void readNBT(Capability<IMachineCapability> pCapability, IMachineCapability pInstance, Direction pSide, INBT pData) {
        pInstance.setNBTData((CompoundNBT) pData);
    }
}
