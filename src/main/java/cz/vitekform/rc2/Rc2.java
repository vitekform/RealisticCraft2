package cz.vitekform.rc2;

import cz.vitekform.rc2.events.*;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class Rc2 implements ModInitializer {

    @Override
    public void onInitialize() {
        ModItems.initialize();
        logChoppingFix.init();
    }
}
