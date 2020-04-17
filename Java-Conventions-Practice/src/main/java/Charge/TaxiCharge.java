package Charge;

public class TaxiCharge implements Chargable {
    public double calculate(int age, int kiloMeter) {
        double baseTaxiCharge = 3000;

        return (baseTaxiCharge * kiloMeter);
    }
}
