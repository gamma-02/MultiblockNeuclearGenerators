package gamma02.neucleargenerators.common;

import gamma02.neucleargenerators.registration.Registerer;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import static gamma02.neucleargenerators.NeuclearGenerators.modid;

public class Tags {
    public static final TagKey<Block> VALID_REACTOR_INTERNAL = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "valid_reactor_internals"));
    public static final TagKey<Fluid> VALID_REACTOR_INTERNAL_COOLANT = TagKey.of(Registry.FLUID_KEY, new Identifier("c", "valid_reactor_internal_coolant"));


}
