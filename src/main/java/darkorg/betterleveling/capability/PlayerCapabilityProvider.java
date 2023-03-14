package darkorg.betterleveling.capability;

import darkorg.betterleveling.impl.PlayerCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(PlayerCapability.class)
    public static Capability<PlayerCapability> PLAYER_CAP = null;

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
    public CompoundNBT serializeNBT() {

        return getCapability().getData();
    }

    @Override
    public void deserializeNBT(CompoundNBT pData) {
        getCapability().setData(pData);
    }
}
