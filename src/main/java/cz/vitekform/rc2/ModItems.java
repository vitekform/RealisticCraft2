package cz.vitekform.rc2;

import cz.vitekform.rc2.internal.RCItemEnergyStorage;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class ModItems {
    public static Item register(Item item, RegistryKey<Item> registryKey) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

        // Return the registered item!
        ItemStack stack = new ItemStack(registeredItem);
        return registeredItem;
    }

    public static void processItemTags(ItemStack stack) {
        if (stack != null) {
            if (!stack.isEmpty()) {
                if (!stack.contains(ModComponents.TOTAL_ITEM_ENERGY_COMPONENT)) {
                    double nVal = RCItemEnergyStorage.getRaw(stack);
                    stack.set(ModComponents.TOTAL_ITEM_ENERGY_COMPONENT, nVal);
                }
                if (!stack.contains(ModComponents.TOTAL_BURNABLE_CHEMICAL_ENERGY_COMPONENT)) {
                    double nVal = RCItemEnergyStorage.getBurnableChemical(stack);
                    stack.set(ModComponents.TOTAL_BURNABLE_CHEMICAL_ENERGY_COMPONENT, nVal);
                }
                if (!stack.contains(ModComponents.REQUIRED_ENERGY_FOR_REFINE_COMPONENT)) {
                    double nVal = RCItemEnergyStorage.getRefine(stack);
                    stack.set(ModComponents.REQUIRED_ENERGY_FOR_REFINE_COMPONENT, nVal);
                }
            }
        }
    }

    private static final String MOD_ID = "rc2";

    public static final RegistryKey<Item> WOOD_BARK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "wood_bark"));
    public static final Item WOOD_BARK = register(
            new Item(new Item.Settings()),
            WOOD_BARK_KEY
    );

    public static final RegistryKey<Item> ROCK = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "rock"));
    public static final Item ROCK_ITEM = register(
            new Item(new Item.Settings()),
            ROCK
    );

    public static final RegistryKey<Item> SHARP_ROCK = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "sharp_rock"));
    public static final Item SHARP_ROCK_ITEM = register(
            new Item(new Item.Settings()),
            SHARP_ROCK
    );

    public static void initialize() {
        FuelRegistry.INSTANCE.add(ModItems.WOOD_BARK, 40);
    }
}
