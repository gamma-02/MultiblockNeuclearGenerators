package gamma02.neucleargenerators.common.screens;

import gamma02.neucleargenerators.common.blocks.ModBlockEntities;
import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class ControllerScreenHandler extends AbstractReactorScreenHandler{
    public ControllerScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }


    public ControllerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory1) {
        super(syncId, playerInventory, inventory1);
    }

    public ControllerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory1, ControllerBlockEntity owner) {
        super(syncId, playerInventory, inventory1, owner);
    }
    public ControllerScreenHandler(int syncId, PlayerInventory playerInventory, ControllerBlockEntity owner) {
        super(syncId, playerInventory, owner);
    }

    public ControllerScreenHandler(int i, PlayerInventory inventory) {
        super(ModScreens.REACTOR_CONTROLLER_SCREEN_HANDLER, i, inventory);
    }
}
