package gamma02.neucleargenerators.common.blocks;

import gamma02.neucleargenerators.common.blocks.casings.CasingBlockEntity;
import gamma02.neucleargenerators.common.blocks.casings.InternalCasingBlockEntity;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import gamma02.neucleargenerators.registration.Registerer;
import gamma02.neucleargenerators.registration.RegistryEntry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static gamma02.neucleargenerators.NeuclearGenerators.modid;

@Registerer(modid = modid, element = BlockEntityType.class)
public class ModBlockEntities {

    @RegistryEntry("controller_block_entity_type")
    public static BlockEntityType<ControllerBlockEntity> CONTROLLER_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(ControllerBlockEntity::new, ModBlocks.CONTROLLER_TEIR_ONE).build();

    @RegistryEntry("casing_block_entity_type")
    public static BlockEntityType<CasingBlockEntity<CasingBlockEntity>> CASING_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(CasingBlockEntity::new, ModBlocks.CASING_TEIR_ONE).build();

    @RegistryEntry("internal_casing_block_entity_type_temp")
    public static BlockEntityType<InternalCasingBlockEntity> INTERNAL_CASING_BLOCK_ENTITY_TYPE_TEMP = FabricBlockEntityTypeBuilder.create(InternalCasingBlockEntity::new, ModBlocks.INTERNAL_CASING_BLOCK_TEMP).build();


}
