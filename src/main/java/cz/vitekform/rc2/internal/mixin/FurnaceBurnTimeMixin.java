package cz.vitekform.rc2.internal.mixin;

import cz.vitekform.rc2.ModComponents;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceBurnTimeMixin {
    @Inject(method = "getCookTime", at = @At("RETURN"), cancellable = true)
    private static void modifyCookTime(World world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> cir) {
        // Default cook time is 200 ticks (10 seconds)
        int baseCookTime = cir.getReturnValue();

        // Get the smelting item from slot 0
        ItemStack smeltingItem = furnace.getStack(0);
        ItemStack fuelItem = furnace.getStack(1);

        // Modify cook time based on item properties
        double smeltingItemReqEnergy = smeltingItem.getOrDefault(ModComponents.REQUIRED_ENERGY_FOR_REFINE_COMPONENT, -1.0);
        double fuelItemEnergy = fuelItem.getOrDefault(ModComponents.TOTAL_BURNABLE_CHEMICAL_ENERGY_COMPONENT, -1.0);
        // If smeltingItemReqEnergy isn't set OR fuelItemEnergy isn't set we don't modify anything

        if (smeltingItemReqEnergy != -1.0 && fuelItemEnergy != -1.0) {
            // The modifier is the ratio of the required energy to the energy of the fuel
            double modifier = smeltingItemReqEnergy / fuelItemEnergy;
            // Modify the cook time
            cir.setReturnValue((int)(baseCookTime * modifier));
        }
    }
}