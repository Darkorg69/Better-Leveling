package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.BlockEntityCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class BlockEntityCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<BlockEntityCapability> BLOCK_ENTITY_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    private BlockEntityCapability instance;
    private final LazyOptional<BlockEntityCapability> optional = LazyOptional.of(this::getCapability);

    private BlockEntityCapability getCapability() {
        return this.instance == null ? this.instance = new BlockEntityCapability() : this.instance;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> pCapability, Direction pSide) {
        return BLOCK_ENTITY_CAP.orEmpty(pCapability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getCapability().getData();
    }

    @Override
    public void deserializeNBT(CompoundTag pData) {
        getCapability().setData(pData);
    }
}
