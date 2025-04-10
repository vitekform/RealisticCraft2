package cz.vitekform.rc2.classes;

public record ElectricTransfer(double voltage, double amperage) {
    public double getPower() {
        return voltage * amperage;
    }
}
