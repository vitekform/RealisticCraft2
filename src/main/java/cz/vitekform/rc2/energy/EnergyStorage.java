package cz.vitekform.rc2.energy;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class EnergyStorage implements EnergyHandler {
    private double voltage;
    private double amperage;
    private final double maxVoltage;
    private final double maxAmperage;

    public EnergyStorage(double maxVoltage, double maxAmperage) {
        this.maxVoltage = maxVoltage;
        this.maxAmperage = maxAmperage;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getAmperage() {
        return amperage;
    }

    @Override
    public double getPower() {
        return voltage * amperage;
    }

    @Override
    public void setVoltage(double voltage) {
        this.voltage = Math.min(voltage, maxVoltage);
    }

    @Override
    public void setAmperage(double amperage) {
        this.amperage = Math.min(amperage, maxAmperage);
    }

    @Override
    public void transferEnergy(EnergyHandler target, double maxAmperage) {
        try (Transaction transaction = Transaction.openOuter()) {
            double transferAmperage = Math.min(this.amperage, maxAmperage);
            target.setVoltage(this.voltage);
            target.setAmperage(transferAmperage);
            this.amperage -= transferAmperage;

            // Commit the transaction if everything is successful
            transaction.commit();
        } catch (Exception e) {
            // Transaction will automatically roll back if not committed
            System.err.println("Energy transfer failed: " + e.getMessage());
        }
    }
}