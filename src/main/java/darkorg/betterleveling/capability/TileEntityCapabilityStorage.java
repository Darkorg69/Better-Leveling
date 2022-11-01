package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.ITileCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityCapabilityStorage implements Capability.IStorage<ITileCapability> {
    @Override
    public INBT writeNBT(Capability<ITileCapability> capability, ITileCapability instance, Direction side) {
        return instance.writeNBT();
    }

    @Override
    public void readNBT(Capability<ITileCapability> capability, ITileCapability instance, Direction side, INBT nbt) {
        instance.readNBT((CompoundNBT) nbt);
    }
}
