package gamma02.neucleargenerators.common.screens;

import gamma02.neucleargenerators.common.blocks.controller.ControllerBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractReactorScreenHandler extends ScreenHandler {
    PropertyDelegate delegate;

    @Environment(EnvType.SERVER)
    public ControllerBlockEntity owner;

    PlayerInventory playerInventory;
    Inventory inventory1;
    protected AbstractReactorScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    protected AbstractReactorScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory){
        super(type, syncId);
        this.playerInventory = inventory;
    }

    protected AbstractReactorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory1){
        super(null, syncId);
        this.delegate = new ArrayPropertyDelegate(4);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.playerInventory = playerInventory;
        this.inventory1 = inventory1;

    }



    @Environment(EnvType.SERVER)
    protected AbstractReactorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory1, ControllerBlockEntity owner){
        this(syncId, playerInventory, inventory1);
        this.owner = owner;
    }

    public int getCookProgress() {
        int i = this.delegate.get(2);
        int j = this.delegate.get(3);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }

    public boolean isBurning() {
        return this.delegate.get(0) > 0;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
