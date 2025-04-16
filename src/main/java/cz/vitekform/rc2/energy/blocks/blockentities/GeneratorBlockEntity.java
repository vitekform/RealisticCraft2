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

public class GeneratorBlockEntity extends BlockEntity implements ElectricDevice, BlockEntityTicker<GeneratorBlockEntity> {
    private double storedEnergy = 0.0;

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERATOR_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, GeneratorBlockEntity blockEntity) {
        if (!world.isClient) {
            double generatedPower = getVoltage() * getAmperage(); // W = V × A
            storedEnergy = Math.min(getMaxEnergy(), storedEnergy + generatedPower);

            ElectricTransfer out = extractPower(getVoltage(), getAmperage());

            for (Direction dir : Direction.values()) {
                BlockEntity neighbor = world.getBlockEntity(pos.offset(dir));
                if (neighbor instanceof ElectricDevice device) {
                    if (device.getVoltage() == getVoltage()) {
                        device.receivePower(out);
                    }
                }
            }
        }
    }

    @Override public double getVoltage() { return 32.0; }
    @Override public double getAmperage() { return 1.0; }
    @Override public double getStoredEnergy() { return storedEnergy; }
    @Override public double getMaxEnergy() { return 10_000.0; }

    @Override
    public void receivePower(ElectricTransfer transfer) {
        // generators don’t accept power
    }

    @Override
    public ElectricTransfer extractPower(double maxVoltage, double maxAmperage) {
        if (maxVoltage < getVoltage()) return new ElectricTransfer(0, 0);

        double potentialPower = Math.min(storedEnergy, getVoltage() * maxAmperage);
        double actualAmperage = potentialPower / getVoltage();

        storedEnergy -= potentialPower;
        return new ElectricTransfer(getVoltage(), actualAmperage);
    }
}
