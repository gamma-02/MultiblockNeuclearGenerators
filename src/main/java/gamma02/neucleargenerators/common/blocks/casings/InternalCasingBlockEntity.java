package gamma02.neucleargenerators.common.blocks.casings;

import gamma02.neucleargenerators.common.blocks.ModBlockEntities;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class InternalCasingBlockEntity extends CasingBlockEntity {
    public InternalCasingBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.INTERNAL_CASING_BLOCK_ENTITY_TYPE_TEMP, pos, state);
    }
}
