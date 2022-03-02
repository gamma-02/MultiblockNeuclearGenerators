package gamma02.neucleargenerators.common.blocks;

import gamma02.neucleargenerators.NeuclearGenerators;
import gamma02.neucleargenerators.common.blocks.casings.CasingBlock;
import gamma02.neucleargenerators.common.blocks.casings.InternalCasingBlock;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlock;
import gamma02.neucleargenerators.registration.Registerer;
import gamma02.neucleargenerators.registration.RegistryEntry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

@Registerer(modid = NeuclearGenerators.modid, element = Block.class)
public class ModBlocks {

    @RegistryEntry("basic_controller")
    public static final ControllerBlock CONTROLLER_TEIR_ONE = new ControllerBlock(FabricBlockSettings.of(Material.METAL), (short) 1);

    @RegistryEntry("basic_casing")
    public static final CasingBlock CASING_TEIR_ONE = new CasingBlock(FabricBlockSettings.of(Material.METAL));

    @RegistryEntry("internal_casing_temp")
    public static final InternalCasingBlock INTERNAL_CASING_BLOCK_TEMP = new InternalCasingBlock(FabricBlockSettings.of(Material.METAL));

}
