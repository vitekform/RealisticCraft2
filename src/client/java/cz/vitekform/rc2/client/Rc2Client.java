package cz.vitekform.rc2.client;

import cz.vitekform.rc2.internal.cutils.crafting_pad.CP_ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class Rc2Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(CP_ScreenHandlers.PEBBLE_CRAFTING_SCREEN_HANDLER, CP_CraftingScreen::new);
    }
}