package Charge;

public class SubwayCharge implements Chargable {
    public double calculate(int age, int kiloMeter) {
        double baseSubwayCharge = 1000;
        double addCost = 1;

        if (age < 15) {
            addCost = 0.5;
        }
        else if (age > 60) {
            addCost = 0.7;
        }
        if (kiloMeter > 50) {
            addCost = 1.5;
        }

        return (baseSubwayCharge * addCost);
    }
}
