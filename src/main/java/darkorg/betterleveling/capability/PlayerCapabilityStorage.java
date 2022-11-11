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
    public INBT writeNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side) {
        return instance.getNBTData();
    }

    @Override
    public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side, INBT nbt) {
        instance.setNBTData((CompoundNBT) nbt);
    }
}
