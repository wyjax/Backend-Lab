package Charge;

public class BusCharge implements Chargable {
    public double calculate(int age, int kiloMeter) {
        double baseBusCharge = 600;
        double addCost = 1;

        if (age < 15) {
            addCost = 0.5;
        }
        else if (age > 60) {
            addCost = 0.7;
        }

        return (baseBusCharge * addCost);
    }
}
