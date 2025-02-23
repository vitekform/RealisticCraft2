package cz.vitekform.rc2.internal;

public class FixedItemEnergyData {

    public final double totalEnergy; // In GJ
    public final double burnableChemicalEnergy;
    public final double refineEnergy;

    public long totalEnergy() {
        return (long) (totalEnergy * 1_000_000_000);
    }

    public long burnableChemicalEnergy() {
        return (long) (burnableChemicalEnergy * 1_000_000_000);
    }

    public long refineEnergy() {
        return (long) (refineEnergy * 1_000_000_000);
    }

    public FixedItemEnergyData(double totalEnergy, double burnableChemicalEnergy, double refineEnergy) {
        this.totalEnergy = totalEnergy;
        this.burnableChemicalEnergy = burnableChemicalEnergy;
        this.refineEnergy = refineEnergy;
    }
}
