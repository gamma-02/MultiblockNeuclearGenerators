package gamma02.neucleargenerators;

import gamma02.neucleargenerators.common.blocks.ModBlockEntities;
import gamma02.neucleargenerators.common.blocks.ModBlocks;
import gamma02.neucleargenerators.registration.RegisteryHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;

public class NeuclearGenerators implements ModInitializer {
    public final static String modid = "multiblock_neuclear_generators";
    @Override
    public void onInitialize() {
        RegisteryHelper.register(ModBlocks.class, Registry.BLOCK);
        RegisteryHelper.register(ModBlockEntities.class, Registry.BLOCK_ENTITY_TYPE);
    }
}
