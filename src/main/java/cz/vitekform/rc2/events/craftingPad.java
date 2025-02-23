package cz.vitekform.rc2.events;

import cz.vitekform.rc2.ModBlocks;
import cz.vitekform.rc2.internal.cutils.crafting_pad.CP_ScreenHandler;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class craftingPad {

    public static void init() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getBlockState(hitResult.getBlockPos()).getBlock() == ModBlocks.CRAFTING_PAD) {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                        (syncId, inv, p) -> new CP_ScreenHandler(syncId, inv, hitResult.getBlockPos()),
                        Text.literal("Crafting Pad")
                ));
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }
}
