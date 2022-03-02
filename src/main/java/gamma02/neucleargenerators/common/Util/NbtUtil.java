package gamma02.neucleargenerators.common.Util;

import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.system.CallbackI;

public class NbtUtil {
    public static final String INVENTORY_CONTROLLER_KEY = "NuclearInventory";
    public static final String CASING_DATA = "ReactorCasings";
    public static final String INTERNAL_DATA = "ReactorInternals";
    public static final String REACTOR_DATA = "ReactorData";

    public static NbtCompound writeReactor(ControllerBlockEntity entity){


        NbtCompound reactordata = new NbtCompound();

        NbtCompound inventory = new NbtCompound();
        Inventories.writeNbt(inventory, entity.inventory);

        reactordata.putInt("temperature", entity.getTemperature());
        reactordata.putInt("fuelTicksLeft", entity.getFuelTicksLeft());


        NbtCompound casingData = new NbtCompound();
        NbtCompound internalData = new NbtCompound();


        int i = 0;
        for (BlockPos pos : entity.casing_blocks) {
            NbtCompound temp = new NbtCompound();
            temp.putDouble("x", pos.getX());
            temp.putDouble("y", pos.getY());
            temp.putDouble("z", pos.getZ());
            String s = ("casing" + i);
            casingData.put(s, temp);
            i++;
        }


        i = 0;
        for(BlockPos pos: entity.internal_blocks){
            NbtCompound temp = new NbtCompound();
            temp.putDouble("x", pos.getX());
            temp.putDouble("y", pos.getY());
            temp.putDouble("z", pos.getZ());
            String s = ("internal" + i);
            internalData.put(s, temp);
        }


        reactordata.put(CASING_DATA, casingData);
        reactordata.put(INTERNAL_DATA, internalData);
        reactordata.put(INVENTORY_CONTROLLER_KEY, inventory);
        return reactordata;
    }

    public static void readReactorData(NbtCompound compound, ControllerBlockEntity entity){
        NbtCompound reactorData = compound.getCompound(REACTOR_DATA);
        DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2);
        Inventories.readNbt(reactorData.getCompound(INVENTORY_CONTROLLER_KEY), inventory);
        entity.setInvStackList(inventory);

        entity.setFuelTicksLeft(reactorData.getInt("fuelTicksLeft"));
        entity.setTemperature(reactorData.getInt("temperature"));

        NbtCompound casingData = compound.getCompound(CASING_DATA);
        for(int i = 0; true; i++){
            NbtCompound temp = casingData.getCompound(("casing" + i));
            if(temp == null){break;}
            BlockPos tempPos = fromNbt(temp);
            entity.casing_blocks.add(tempPos);
        }

        NbtCompound internalData = compound.getCompound(INTERNAL_DATA);
        for(int i = 0; true; i++){
            NbtCompound temp = internalData.getCompound(("internal" + i));
            if(temp == null){break;}
            BlockPos tempPos = fromNbt(temp);
            entity.internal_blocks.add(tempPos);
        }
        entity.updateMultiblock();



    }

    public static BlockPos fromNbt(NbtCompound compound){
        return new BlockPos(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z"));
    }
}
