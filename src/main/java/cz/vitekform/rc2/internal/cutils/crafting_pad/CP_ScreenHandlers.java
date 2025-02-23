package cz.vitekform.rc2.internal.cutils.crafting_pad;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CP_ScreenHandlers {
    public static final ScreenHandlerType<CP_ScreenHandler> PEBBLE_CRAFTING_SCREEN_HANDLER;

    static {
        PEBBLE_CRAFTING_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of("rc2", "crafting_pad_menu"),
                new ScreenHandlerType<>(CP_ScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
        );
    }

    public static void registerScreenHandlers() {
        // Registration is handled in static initializer
    }
}