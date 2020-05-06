
public class cp_1_1 {
    public static void main(String[] args) {
        ChampionModel rise = Champion.create("Rize");
        ChampionModel ezreal = Champion.create("Ezreal");
    }
}

class Champion {
    public static ChampionModel create(String championName) {
        if (championName.equals("Rize")) {
            return new Rize(100, 100, 10, 10);
        }
        else if (championName.equals("Ezreal")) {
            return new Ezreal(100, 100, 10, 10);
        }
        return null;
    }
}

interface ChampionModel {
    public void userQ();

    public void userR();
}

class Rize implements ChampionModel {
    private int healthPoint;
    private int magicPoint;
    private int qPay;
    private int rPay;

    public Rize(int healthPoint, int magicPoint, int qPay, int rPay) {
        this.healthPoint = healthPoint;
        this.magicPoint = magicPoint;
        this.qPay = qPay;
        this.rPay = rPay;
    }

    @Override
    public void userQ() {
        this.magicPoint -= this.qPay;
    }

    @Override
    public void userR() {
        this.magicPoint -= this.rPay;
    }
}

class Ezreal implements ChampionModel {
    int healthPoint;
    int magicPoint;
    int qPay;
    int rPay;

    public Ezreal(int healthPoint, int magicPoint, int qPay, int rPay) {
        this.healthPoint = healthPoint;
        this.magicPoint = magicPoint;
        this.qPay = qPay;
        this.rPay = rPay;
    }

    @Override
    public void userQ() {
        this.magicPoint -= this.qPay;
    }

    @Override
    public void userR() {
        this.magicPoint -= this.rPay;
    }
}