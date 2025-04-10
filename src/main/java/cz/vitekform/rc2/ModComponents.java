package cz.vitekform.rc2;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static ComponentType<Double> TOTAL_ITEM_ENERGY_COMPONENT;
    public static ComponentType<Double> TOTAL_BURNABLE_CHEMICAL_ENERGY_COMPONENT;
    public static ComponentType<Double> REQUIRED_ENERGY_FOR_REFINE_COMPONENT;
    public static ComponentType<Integer> CRAFT_PAD_USED_TIMES_COMPONENT;

    public static void initialize() {
        TOTAL_ITEM_ENERGY_COMPONENT = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(Rc2.MOD_ID, "total_energy_item"),
                ComponentType.<Double>builder().codec(Codec.DOUBLE).build()
        );

        TOTAL_BURNABLE_CHEMICAL_ENERGY_COMPONENT = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(Rc2.MOD_ID, "total_burnable_chemical_energy"),
                ComponentType.<Double>builder().codec(Codec.DOUBLE).build()
        );

        REQUIRED_ENERGY_FOR_REFINE_COMPONENT = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(Rc2.MOD_ID, "required_energy_for_refine"),
                ComponentType.<Double>builder().codec(Codec.DOUBLE).build()
        );

        CRAFT_PAD_USED_TIMES_COMPONENT = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(Rc2.MOD_ID, "craft_pad_used_times"),
                ComponentType.<Integer>builder().codec(Codec.INT).build()
        );
    }
}