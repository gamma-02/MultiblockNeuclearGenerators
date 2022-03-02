package gamma02.neucleargenerators.common.recipies;

import gamma02.neucleargenerators.common.recipies.nuclearfuel.NuclearFuelRecipie;
import gamma02.neucleargenerators.registration.Registerer;
import gamma02.neucleargenerators.registration.RegistryEntry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

import static gamma02.neucleargenerators.NeuclearGenerators.modid;
@Registerer(modid = modid, element = RecipeType.class)
public class ModRecipies {


    @RegistryEntry(NuclearFuelRecipie.Type.ID)
    public static final RecipeType<NuclearFuelRecipie> NUCLEAR_FUEL_RECIPIE_RECIPE_TYPE = NuclearFuelRecipie.Type.INSTANCE;


    @Registerer(modid = modid, element = RecipeSerializer.class)
    public class RecipieSerializers{
        @RegistryEntry(NuclearFuelRecipie.IdentifierSerializer)
        public static final RecipeSerializer<NuclearFuelRecipie> NUCLEAR_FUEL_RECIPIE_RECIPE_SERIALIZER = new NuclearFuelRecipie.Serializer();
    }
}
