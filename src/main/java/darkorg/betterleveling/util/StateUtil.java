package darkorg.betterleveling.util;

import darkorg.betterleveling.registry.ModTags;
import net.minecraft.world.level.block.state.BlockState;

public class StateUtil {
    public static boolean isCropBlock(BlockState pBlockState) {
        return pBlockState.is(ModTags.Blocks.CROPS);
    }

    public static boolean isTreasureBlock(BlockState pBlockState) {
        return pBlockState.is(ModTags.Blocks.TREASURE_BLOCKS);
    }

}
