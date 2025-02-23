package cz.vitekform.rc2.internal.mixin;

import cz.vitekform.rc2.internal.PlayerInventoryClickCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public class PlayerInventoryClickMixin {
    @Inject(
            method = "handleSlotClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onInventoryClick(
            PlayerEntity player, net.minecraft.util.ClickType clickType, Slot slot, ItemStack stack, ItemStack cursorStack, CallbackInfoReturnable<Boolean> cir
    ) {
        ActionResult result = PlayerInventoryClickCallback.EVENT.invoker().interact(
                player,
                clickType,
                slot,
                stack,
                cursorStack,
                (ScreenHandler) (Object) this
        );

        if (result == ActionResult.FAIL) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}