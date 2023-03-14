package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerCapabilityStorage implements Capability.IStorage<PlayerCapability> {
    @Override
    public INBT writeNBT(Capability<PlayerCapability> pCapability, PlayerCapability pInstance, Direction pSide) {
        return pInstance.getData();
    }

    @Override
    public void readNBT(Capability<PlayerCapability> pCapability, PlayerCapability pInstance, Direction pSide, INBT pData) {
        pInstance.setData((CompoundNBT) pData);
    }
}
