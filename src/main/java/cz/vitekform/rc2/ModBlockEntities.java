package cz.vitekform.rc2;

import cz.vitekform.rc2.blocks.blockentities.BatteryBlockEntity;
import cz.vitekform.rc2.blocks.blockentities.CableBlockEntity;
import cz.vitekform.rc2.blocks.blockentities.GeneratorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<GeneratorBlockEntity> GENERATOR_BLOCK_ENTITY;
    public static BlockEntityType<BatteryBlockEntity> BATTERY_BLOCK_ENTITY;
    public static BlockEntityType<CableBlockEntity> CABLE_BLOCK_ENTITY;

    public static void registerAllBlockEntities() {
        GENERATOR_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(Rc2.MOD_ID, "generator"),
                FabricBlockEntityTypeBuilder.create(GeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK_DEV).build(null)
        );

        BATTERY_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(Rc2.MOD_ID, "battery"),
                FabricBlockEntityTypeBuilder.create(BatteryBlockEntity::new, ModBlocks.BATTERY_BLOCK_DEV).build(null)
        );

        CABLE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(Rc2.MOD_ID, "cable"),
                FabricBlockEntityTypeBuilder.create(CableBlockEntity::new, ModBlocks.CABLE_BLOCK_DEV).build(null)
        );
    }
}
