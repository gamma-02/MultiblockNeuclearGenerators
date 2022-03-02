package gamma02.neucleargenerators.common.blocks.casings;

import gamma02.neucleargenerators.common.blocks.ModBlockEntities;
import gamma02.neucleargenerators.common.blocks.ModBlocks;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CasingBlockEntity<T extends CasingBlockEntity> extends BlockEntity {
    public ControllerBlockEntity owner;
    public CasingBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.CASING_BLOCK_ENTITY_TYPE, pos, state);
    }
    public CasingBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }



    public ControllerBlockEntity getOwner() {
        return owner;
    }

    public void setOwner(ControllerBlockEntity owner) {
        this.owner = owner;
    }
}
