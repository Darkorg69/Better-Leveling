package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.BlockEntityCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BlockEntityCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(BlockEntityCapability.class)
    public static Capability<BlockEntityCapability> BLOCK_ENTITY_CAP = null;

    private BlockEntityCapability instance;

    private final LazyOptional<BlockEntityCapability> optional = LazyOptional.of(this::getCapability);

    private BlockEntityCapability getCapability() {
        return this.instance == null ? this.instance = new BlockEntityCapability() : this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> pCapability, Direction pSide) {
        return BLOCK_ENTITY_CAP.orEmpty(pCapability, optional);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getCapability().getData();
    }

    @Override
    public void deserializeNBT(CompoundNBT pData) {
        getCapability().setData(pData);
    }
}
