package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.ITileCapability;
import darkorg.betterleveling.impl.TileCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class TileEntityCapabilityProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(ITileCapability.class)
    public static Capability<ITileCapability> TILE_CAP = null;

    private final ITileCapability instance;
    private final LazyOptional<ITileCapability> optional;

    public TileEntityCapabilityProvider() {
        this.instance = new TileCapability();
        this.optional = LazyOptional.of(() -> instance);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        return TILE_CAP.orEmpty(cap, optional);
    }

    @Override
    public INBT serializeNBT() {
        return TILE_CAP.writeNBT(instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TILE_CAP.readNBT(instance, null, nbt);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
