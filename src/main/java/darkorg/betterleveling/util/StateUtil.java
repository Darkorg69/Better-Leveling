package darkorg.betterleveling.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class StateUtil {
    public static boolean isBonemealablePlant(BlockPos pPos, ServerLevel pServerLevel) {
        Block block = pServerLevel.getBlockState(pPos).getBlock();

        if (block instanceof BonemealableBlock) {
            if (block instanceof IPlantable plant) {
                PlantType plantType = plant.getPlantType(pServerLevel, pPos);
                return plantType == PlantType.CROP || plantType == PlantType.NETHER;
            }
        }

        return false;
    }

    public static boolean isMaxAgeBonemealableBlock(BlockPos pPos, ServerLevel pServerLevel, BonemealableBlock pBonemealableBlock) {
        return !pBonemealableBlock.isValidBonemealTarget(pServerLevel, pPos, pServerLevel.getBlockState(pPos));
    }
}
