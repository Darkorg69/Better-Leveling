package darkorg.betterleveling.capability;

import darkorg.betterleveling.api.IPlayerCapability;
import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IPlayerCapability.class)
    public static Capability<IPlayerCapability> PLAYER_CAP = null;
    private IPlayerCapability instance;
    private final LazyOptional<IPlayerCapability> optional = LazyOptional.of(this::getCapability);

    private IPlayerCapability getCapability() {
        return this.instance == null ? this.instance = new PlayerCapability() : this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> pCapability, Direction pSide) {
        return PLAYER_CAP.orEmpty(pCapability, optional);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getCapability().getNBTData();
    }

    @Override
    public void deserializeNBT(CompoundNBT pData) {
        getCapability().setNBTData(pData);
    }
}
