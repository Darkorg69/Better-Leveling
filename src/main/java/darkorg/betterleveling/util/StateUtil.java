package darkorg.betterleveling.util;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class StateUtil {
    public static boolean isBonemealablePlant(BlockPos pPos, ServerWorld pServerLevel) {
        Block block = pServerLevel.getBlockState(pPos).getBlock();

        if (block instanceof IGrowable) {
            if (block instanceof IPlantable) {
                IPlantable plant = (IPlantable) block;
                PlantType plantType = plant.getPlantType(pServerLevel, pPos);
                return plantType == PlantType.CROP || plantType == PlantType.NETHER;
            }
        }

        return false;
    }

    public static boolean isMaxAgeBonemealableBlock(BlockPos pPos, ServerWorld pServerLevel, IGrowable pBonemealableBlock) {
        return !pBonemealableBlock.isValidBonemealTarget(pServerLevel, pPos, pServerLevel.getBlockState(pPos), pServerLevel.isClientSide);
    }
}
