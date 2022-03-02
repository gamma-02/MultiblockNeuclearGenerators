package gamma02.neucleargenerators.common.recipies.nuclearfuel;

import com.google.gson.JsonObject;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import gamma02.neucleargenerators.common.recipies.ModRecipies;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class NuclearFuelRecipie implements Recipe<ControllerBlockEntity> {
    public static final String FUEL_JSON_KEY = "fuel_input";
    public static final String SPENT_FUEL_JSON_KEY = "spent_fuel_output";
    public static final String IdentifierSerializer = "nuclear_fuel_recipie_serializer";

    protected final RecipeType<?> type;
    protected final Identifier id;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int burnTime;

    public NuclearFuelRecipie(RecipeType<?> type, Identifier id, Ingredient input, ItemStack output, int burnTime) {
        this.type = type;
        this.id = id;
        this.input = input;
        this.output = output;
        this.burnTime = burnTime;
    }

    @Override
    public boolean matches(ControllerBlockEntity inventory, World world) {
        return this.input.test(inventory.getStack(0)) && inventory.inventory.get(1) == ItemStack.EMPTY || inventory.inventory.get(1).getItem() == this.output.getItem() && inventory.getFuelTicksLeft() == 0;
    }

    @Override
    public ItemStack craft(ControllerBlockEntity inventory) {
        return this.output.copy();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Override
    public boolean fits(int width, int height) {
        return width == 1 && height == 2;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipies.RecipieSerializers.NUCLEAR_FUEL_RECIPIE_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipies.NUCLEAR_FUEL_RECIPIE_RECIPE_TYPE;
    }

    public int getBurnTime(){
        return this.burnTime;
    }

    public static class Serializer implements RecipeSerializer<NuclearFuelRecipie>{

        @Override
        public NuclearFuelRecipie read(Identifier id, JsonObject json) {
//            NbtCompound outputNbt = new NbtCompound();

            return new NuclearFuelRecipie(null, id, Ingredient.fromJson(json.get(FUEL_JSON_KEY)), ShapedRecipe.outputFromJson(json.get(SPENT_FUEL_JSON_KEY).getAsJsonObject()), json.get("burnTime").getAsInt());
        }

        @Override
        public NuclearFuelRecipie read(Identifier id, PacketByteBuf buf) {

            return new NuclearFuelRecipie(null, id, Ingredient.fromPacket(buf), buf.readItemStack(), buf.readInt());
        }

        @Override
        public void write(PacketByteBuf buf, NuclearFuelRecipie recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.burnTime);
        }
    }

    public static class Type implements RecipeType<NuclearFuelRecipie> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {}
        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final String ID = "nuclear_fuel_recipie";
    }

}
