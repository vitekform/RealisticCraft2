package cz.vitekform.rc2.blocks.blockentities;

import cz.vitekform.rc2.ModBlockEntities;
import cz.vitekform.rc2.classes.ElectricDevice;
import cz.vitekform.rc2.classes.ElectricTransfer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BatteryBlockEntity extends BlockEntity implements ElectricDevice {
    private double storedEnergy = 0;

    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
    }

    @Override public double getVoltage() { return 32.0; }
    @Override public double getAmperage() { return 10.0; }
    @Override public double getStoredEnergy() { return storedEnergy; }
    @Override public double getMaxEnergy() { return 100_000.0; }

    @Override
    public void receivePower(ElectricTransfer transfer) {
        if (transfer.voltage() != getVoltage()) return; // strict voltage match

        double acceptedPower = Math.min(getVoltage() * getAmperage(), transfer.getPower());
        storedEnergy = Math.min(getMaxEnergy(), storedEnergy + acceptedPower);
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
