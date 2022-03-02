package gamma02.neucleargenerators.common.screens;

import gamma02.neucleargenerators.registration.Registerer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import static gamma02.neucleargenerators.NeuclearGenerators.modid;

public class ModScreens {

    public static ScreenHandlerType<ControllerScreenHandler> REACTOR_CONTROLLER_SCREEN_HANDLER;

    public void oninit(){
        REACTOR_CONTROLLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(modid, "reactor_controller_screen_handler"), ControllerScreenHandler::new);
    }
}
