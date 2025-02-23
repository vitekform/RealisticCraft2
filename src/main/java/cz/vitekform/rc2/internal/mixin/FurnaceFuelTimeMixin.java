package cz.vitekform.rc2.internal.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceFuelTimeMixin {
    @Inject(method = "getFuelTime", at = @At("RETURN"), cancellable = true)
    private void modifyFuelTime(ItemStack fuel, CallbackInfoReturnable<Integer> cir) {
        AbstractFurnaceBlockEntity furnace = (AbstractFurnaceBlockEntity) (Object) this;
        ItemStack smeltingItem = furnace.getStack(0);

        /*if (!smeltingItem.isEmpty()) {
            int baseBurnTime = cir.getReturnValue();
            double modifier = calculateModifier(smeltingItem);
            cir.setReturnValue((int)(baseBurnTime * modifier));
        }*/
    }

    private static double calculateModifier(ItemStack smeltingItem) {
        if (smeltingItem.isOf(Items.IRON_ORE)) {
            return 0.1; // It will take 10 times more fuel to smelt iron ore
        }
        return 1.0;
    }
}