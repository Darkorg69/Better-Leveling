package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<PlayerCapability> PLAYER_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    private PlayerCapability instance;
    private final LazyOptional<PlayerCapability> optional = LazyOptional.of(this::getCapability);

    private PlayerCapability getCapability() {
        return this.instance == null ? this.instance = new PlayerCapability() : this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> pCapability, Direction pSide) {
        return PLAYER_CAP.orEmpty(pCapability, optional);
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
