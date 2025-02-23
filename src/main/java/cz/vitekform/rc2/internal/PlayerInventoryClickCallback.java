package cz.vitekform.rc2.internal;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;

public interface PlayerInventoryClickCallback {
    Event<PlayerInventoryClickCallback> EVENT = EventFactory.createArrayBacked(
            PlayerInventoryClickCallback.class,
            (listeners) -> (player, clickType, slot, stack, cursorStack, screenHandler) -> {
                for (PlayerInventoryClickCallback listener : listeners) {
                    ActionResult result = listener.interact(player, clickType, slot, stack, cursorStack, screenHandler);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, ClickType clickType, Slot slot, ItemStack stack, ItemStack cursorStack, ScreenHandler screenHandler);
}