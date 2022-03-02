package gamma02.neucleargenerators.common.blocks.controller;

import gamma02.neucleargenerators.common.Tags;
import gamma02.neucleargenerators.common.Util.NbtUtil;
import gamma02.neucleargenerators.common.blocks.ModBlockEntities;
import gamma02.neucleargenerators.common.blocks.casings.CasingBlock;
import gamma02.neucleargenerators.common.blocks.casings.CasingBlockEntity;
import gamma02.neucleargenerators.common.blocks.casings.CasingCornerBlock;
import gamma02.neucleargenerators.common.blocks.casings.InternalCasingBlock;
import gamma02.neucleargenerators.common.recipies.ModRecipies;
import gamma02.neucleargenerators.common.recipies.nuclearfuel.NuclearFuelRecipie;
import gamma02.neucleargenerators.common.screens.ControllerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Arrays;

import static gamma02.neucleargenerators.NeuclearGenerators.modid;

public class ControllerBlockEntity extends LootableContainerBlockEntity implements Inventory {
    private boolean validMultiblock;
    private int temperature;
    private int fuelTicksLeft = 0;
    private int[] offsetStore = {1, 1, 1, 1, 1};//index 0: counterclockwise from facing, index 1: clockwise from facing, index 2: up, index 3: down, index 4: opposite of facing
    public ArrayList<BlockPos> invalid_blocks = new ArrayList<>();
    public ArrayList<BlockPos> invalid_casing_blocks = new ArrayList<>();
    public ArrayList<BlockPos> internal_blocks;
    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public ArrayList<BlockPos> casing_blocks = new ArrayList<>();

    public ControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONTROLLER_BLOCK_ENTITY_TYPE, pos, state);
        this.validMultiblock = updateMultiblock();
        this.temperature = 0;
    }

    //ticker stuff
    public static BlockEntityTicker<ControllerBlockEntity> ticker(){
        return (world, pos, state, blockEntity) -> blockEntity.tick();
    }

    public void tick(){
        for(NuclearFuelRecipie recipie : world.getRecipeManager().getAllMatches(ModRecipies.NUCLEAR_FUEL_RECIPIE_RECIPE_TYPE, this, world)){
            if(recipie.matches(this, world)){
                this.getInput().decrement(1);
                this.setFuelTicksLeft(recipie.getBurnTime());
                if(this.fuelTicksLeft == 0) {
                    if (this.inventory.get(1) == ItemStack.EMPTY) {
                        this.inventory.set(1, recipie.craft(this));
                    }else{
                        this.inventory.get(1).increment(1);
                    }
                }
            }

        }
        if(this.fuelTicksLeft != 0){
            this.fuelTicksLeft--;
        }
        if(this.temperature >= 2800){
            this.doMeltdown();
        }
    }
    //end ticker stuff

    public void doMeltdown(){
        //TODO: later
    }

    //nbt stuff
    @Override
    public void readNbt(NbtCompound nbt) {
        NbtUtil.readReactorData(nbt, this);
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        nbt.put(NbtUtil.REACTOR_DATA, NbtUtil.writeReactor(this));
        super.writeNbt(nbt);
    }
    //end nbt stuff

    public ItemStack getInput(){
        return this.inventory.get(0);
    }


    @Override
    protected Text getContainerName() {
        return new TranslatableText("container." + modid + ".controller_title");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ControllerScreenHandler(syncId, playerInventory, this, this);
    }

    public boolean updateMultiblock(){
        boolean continueIteration = true;

        while (continueIteration){
            boolean[] offsetContinue = {true, true, true, true, true};
            if(world.getBlockState(this.pos.up(offsetStore[2])).getBlock() instanceof CasingBlock){
                offsetStore[2]++;
            }else{
                offsetContinue[0] = false;
            }
            if(world.getBlockState(this.pos.down(offsetStore[3])).getBlock() instanceof CasingBlock){
                offsetStore[3]++;
            }else{
                offsetContinue[1] = false;
            }
            if(world.getBlockState(this.pos.offset(this.getCachedState().get(Properties.FACING).rotateCounterclockwise(Direction.Axis.Y), offsetStore[0])).getBlock() instanceof CasingBlock){
                offsetStore[0]++;
            }else{
                offsetContinue[2] = false;
            }
            if(world.getBlockState(this.pos.offset(this.getCachedState().get(Properties.FACING).rotateClockwise(Direction.Axis.Y), offsetStore[1])).getBlock() instanceof CasingBlock){
                offsetStore[1]++;
            }else{
                offsetContinue[3] = false;
            }
            if(world.getBlockState(this.pos.offset(this.getCachedState().get(Properties.FACING), offsetStore[4])).getBlock() instanceof InternalCasingBlock || world.getBlockState(this.pos.offset(this.getCachedState().get(Properties.FACING), offsetStore[4])).getBlock() == Blocks.AIR){
                offsetStore[4]++;
            }else{
                offsetContinue[4] = false;
            }
            continueIteration = (offsetContinue[0] || offsetContinue[1] || offsetContinue[2] || offsetContinue[3] || offsetContinue[4]);
        }
        BlockPos.Mutable pos1 = this.pos.mutableCopy();
        pos1.offset(this.getCachedState().get(Properties.FACING).rotateCounterclockwise(Direction.Axis.Y), offsetStore[0]);
        pos1.offset(Direction.DOWN, offsetStore[3]);
        pos1.offset(this.getCachedState().get(Properties.FACING).getOpposite(), offsetStore[4]);
        BlockPos.Mutable pos2 = this.pos.mutableCopy();
        pos2.offset(this.getCachedState().get(Properties.FACING).rotateClockwise(Direction.Axis.Y), offsetStore[1]);
        pos2.offset(Direction.UP, offsetStore[2]);

        Box box = new Box(pos1.toImmutable(), pos2.toImmutable());
        Box box2 = box.shrink(1, 1, 1);
        for(double x = box2.minX; x <= box2.maxX; x++) {
            for (double y = box2.minY; y <= box2.maxY; y++) {
                for (double z = box2.minZ; z <= box2.maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!world.getBlockState(pos).getBlock().getRegistryEntry().streamTags().anyMatch(blockTagKey -> blockTagKey.equals(Tags.VALID_REACTOR_INTERNAL) || blockTagKey.equals(Tags.VALID_REACTOR_INTERNAL_COOLANT))) {
                        invalid_blocks.add(pos);
                    }else{

                    }
                }
            }
        }
        for(double x = box.minX; x <= box.maxX; x++){
            for(double y = box.minY; y <= box.maxY; y++){
                for(double z = box.minZ; z <= box.maxZ; z++){
                    if(isOnMaxOrMinX(x, box) || isOnMaxOrMinY(y, box) || isOnMaxOrMinZ(z, box)){
                        BlockPos pos3 = new BlockPos(x, y, z);
                        if(!(world.getBlockState(pos3).getBlock() instanceof CasingBlock) || !(world.getBlockState(pos3).getBlock() instanceof CasingCornerBlock)){
                            invalid_casing_blocks.add(pos3);
                        }else{
                            casing_blocks.add(pos3);
                        }
                    }
                }
            }
        }
        Arrays.stream(offsetStore).forEach(i -> i = 0);

        return invalid_blocks.isEmpty() && invalid_casing_blocks.isEmpty();
    }

    public void updateCasings(){
        this.validMultiblock = this.updateMultiblock();
        for(BlockPos pos : casing_blocks){
            if(world.getBlockEntity(pos) instanceof CasingBlockEntity b){
                b.setOwner(this);
            }
        }
    }

    public static boolean isOnMaxOrMinX(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minX || i == box.maxX;
    }

    public static boolean isOnMaxOrMinY(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minY || i == box.maxY;
    }

    public static boolean isOnMaxOrMinZ(double i, Box box) {
        if (box == null) {
            return false;
        }
        return i == box.minZ || i == box.maxZ;
    }


    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    public void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public int size() {
        return 2;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getFuelTicksLeft() {
        return fuelTicksLeft;
    }

    public void setFuelTicksLeft(int fuelTicksLeft) {
        this.fuelTicksLeft = fuelTicksLeft;
    }
}
