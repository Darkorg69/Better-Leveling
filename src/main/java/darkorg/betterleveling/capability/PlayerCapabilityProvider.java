package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class PlayerCapabilityProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IPlayerCapability.class)
    public static Capability<IPlayerCapability> PLAYER_CAP = null;

    private final IPlayerCapability instance;
    private final LazyOptional<IPlayerCapability> optional;

    public PlayerCapabilityProvider() {
        this.instance = new PlayerCapability();
        this.optional = LazyOptional.of(() -> instance);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        return PLAYER_CAP.orEmpty(cap, optional);
    }

    @Override
    public INBT serializeNBT() {
        return PLAYER_CAP.writeNBT(instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PLAYER_CAP.readNBT(instance, null, nbt);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
