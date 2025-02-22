package cz.vitekform.rc2;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item register(Item item, RegistryKey<Item> registryKey) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

        // Return the registered item!
        return registeredItem;
    }

    private static final String MOD_ID = "rc2";

    public static final RegistryKey<Item> WOOD_BARK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "wood_bark"));
    public static final Item WOOD_BARK = register(
            new Item(new Item.Settings()),
            WOOD_BARK_KEY
    );

    public static void initialize() {
    }
}
