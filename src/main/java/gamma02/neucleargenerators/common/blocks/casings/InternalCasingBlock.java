package gamma02.neucleargenerators.common.blocks.casings;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class InternalCasingBlock extends CasingBlock {
    public InternalCasingBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InternalCasingBlockEntity(pos, state);
    }

}
