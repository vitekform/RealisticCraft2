package cz.vitekform.rc2.internal;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.HashMap;
import java.util.Map;

public class RCItemEnergyStorage {

    private static final Map<String, FixedItemEnergyData> energies = new HashMap<>();
    private static void init() {
        if (energies.isEmpty()) {
            energies.put("minecraft:coal", new FixedItemEnergyData((32.4 / 9), 32.4 / 9, -1));
            energies.put("minecraft:charcoal", new FixedItemEnergyData((9.6 / 9), 9.6 / 9, -1));
            energies.put("st:any_log", new FixedItemEnergyData((10D), (double) ((10) / 100) * 80, -1));
            energies.put("st:any_plank", new FixedItemEnergyData(12.6, 12.6, -1));
            energies.put("minecraft:iron_ore", new FixedItemEnergyData(-1, -1, (12.6 / 9)));
            energies.put("rc2:wood_bark", new FixedItemEnergyData(-1, -1, 0.494));
        }
    }

    public static double getRaw(ItemStack item) {
        init();
        String id = Registries.ITEM.getId(item.getItem()).getNamespace() + ":" + Registries.ITEM.getId(item.getItem()).getPath();
        if (energies.containsKey(id)) {
            return energies.get(id).totalEnergy;
        }
        else {
            return -1;
        }
    }

    public static double getBurnableChemical(ItemStack item) {
        init();
        String id = Registries.ITEM.getId(item.getItem()).getNamespace() + ":" + Registries.ITEM.getId(item.getItem()).getPath();
        if (energies.containsKey(id)) {
            return energies.get(id).burnableChemicalEnergy;
        }
        else {
            return -1;
        }
    }

    public static double getRefine(ItemStack item) {
        init();
        String id = Registries.ITEM.getId(item.getItem()).getNamespace() + ":" + Registries.ITEM.getId(item.getItem()).getPath();
        if (energies.containsKey(id)) {
            return energies.get(id).refineEnergy;
        }
        else {
            return -1;
        }
    }
}
