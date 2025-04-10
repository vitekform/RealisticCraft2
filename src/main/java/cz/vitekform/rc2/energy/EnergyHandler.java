package cz.vitekform.rc2.energy;

public interface EnergyHandler {
    double getVoltage();
    double getAmperage();
    double getPower(); // Power = Voltage * Amperage

    void setVoltage(double voltage);
    void setAmperage(double amperage);

    void transferEnergy(EnergyHandler target, double maxAmperage);
}