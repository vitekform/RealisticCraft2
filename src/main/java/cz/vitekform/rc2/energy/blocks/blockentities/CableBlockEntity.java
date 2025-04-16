package cz.vitekform.rc2.energy.blocks.blockentities;

import cz.vitekform.rc2.ModBlockEntities;
import cz.vitekform.rc2.classes.ElectricDevice;
import cz.vitekform.rc2.classes.ElectricTransfer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CableBlockEntity extends BlockEntity implements BlockEntityTicker<CableBlockEntity> {
    public static final double MAX_AMPS = 8.0;
    public static final double VOLTAGE_DROP = 0.5; // simulate resistance

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CableBlockEntity blockEntity) {
        if (world.isClient) return;

        // Find power sources and consumers in adjacent blocks
        for (Direction sourceDir : Direction.values()) {
            BlockEntity sourceEntity = world.getBlockEntity(pos.offset(sourceDir));
            if (!(sourceEntity instanceof ElectricDevice source)) continue;

            // Extract power from the source
            ElectricTransfer power = source.extractPower(Double.MAX_VALUE, MAX_AMPS);
            if (power.amperage() <= 0) continue;

            // Apply voltage drop due to cable resistance
            double transferVoltage = Math.max(0, power.voltage() - VOLTAGE_DROP);
            ElectricTransfer modifiedPower = new ElectricTransfer(transferVoltage, power.amperage());

            // Try to distribute power to other adjacent blocks
            for (Direction targetDir : Direction.values()) {
                if (targetDir == sourceDir.getOpposite()) continue; // Skip the source direction

                BlockEntity targetEntity = world.getBlockEntity(pos.offset(targetDir));
                if (targetEntity instanceof ElectricDevice target) {
                    target.receivePower(modifiedPower);
                    break; // Transfer to only one consumer per source
                }
            }
        }
    }
}