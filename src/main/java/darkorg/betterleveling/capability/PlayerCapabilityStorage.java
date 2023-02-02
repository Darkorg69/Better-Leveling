package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IPlayerCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerCapabilityStorage implements Capability.IStorage<IPlayerCapability> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerCapability> pCapability, IPlayerCapability pInstance, Direction pSide) {
        return pInstance.getNBTData();
    }

    @Override
    public void readNBT(Capability<IPlayerCapability> pCapability, IPlayerCapability pInstance, Direction pSide, INBT pData) {
        pInstance.setNBTData((CompoundNBT) pData);
    }
}
