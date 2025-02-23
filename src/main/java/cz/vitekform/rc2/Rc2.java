package cz.vitekform.rc2;

import cz.vitekform.rc2.events.*;
import cz.vitekform.rc2.internal.cutils.crafting_pad.CP_ScreenHandlers;
import net.fabricmc.api.ModInitializer;

public class Rc2 implements ModInitializer {

    public static final String MOD_ID = "rc2";

    @Override
    public void onInitialize() {
        ModComponents.initialize();
        ModItems.initialize();
        ModBlocks.init();
        logChoppingFix.init();
        invChecker.init();
        rockSharpening.init();
        craftingPad.init();
        CP_ScreenHandlers.registerScreenHandlers();
    }
}
