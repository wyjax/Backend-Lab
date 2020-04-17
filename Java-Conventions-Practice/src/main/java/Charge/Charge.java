package Charge;

public class Charge {
    public static final int BUS = 0;
    public static final int TAXI = 1;
    public static final int SUBWAY = 2;

    public double calculate(String type, int age, int killoMeter) {
        return create(type).calculate(age, killoMeter);
    }

    public static Chargable create(String type) {
        try {
            return (Chargable) Class.forName(type + "Charge").newInstance();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }
}