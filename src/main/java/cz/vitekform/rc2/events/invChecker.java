package cz.vitekform.rc2.events;

import cz.vitekform.rc2.ModItems;
import cz.vitekform.rc2.internal.PlayerInventoryClickCallback;
import net.minecraft.util.ActionResult;

public class invChecker {

    public static void init() {
        PlayerInventoryClickCallback.EVENT.register((player, clickType, slot, itemStack, cursorStack, screenHandler) -> {
            ModItems.processItemTags(itemStack);
            ModItems.processItemTags(cursorStack);
            return ActionResult.PASS;
        });
    }
}
