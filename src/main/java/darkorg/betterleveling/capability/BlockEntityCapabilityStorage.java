package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.BlockEntityCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class BlockEntityCapabilityStorage implements Capability.IStorage<BlockEntityCapability> {
    @Override
    public INBT writeNBT(Capability<BlockEntityCapability> pCapability, BlockEntityCapability pInstance, Direction pSide) {
        return pInstance.getData();
    }

    @Override
    public void readNBT(Capability<BlockEntityCapability> pCapability, BlockEntityCapability pInstance, Direction pSide, INBT pData) {
        pInstance.setData((CompoundNBT) pData);
    }
}
