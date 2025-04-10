package cz.vitekform.rc2.classes;

public interface ElectricDevice {
    double getVoltage(); // rated voltage
    double getAmperage(); // max input/output amperage
    double getStoredEnergy(); // in joules (Watt-seconds)
    double getMaxEnergy();

    void receivePower(ElectricTransfer transfer);
    ElectricTransfer extractPower(double maxVoltage, double maxAmperage);
}
