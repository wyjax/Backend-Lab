
public class cp_1_1 {
    public static void main(String[] args) {
        ChampionModel ryze1 = Champion.create("Rize");
        ChampionModel ezreal1 = Champion.create("Ezreal");

        ChampionModel ryze2 = Champion.create("Rize");
        ChampionModel ezreal2 = Champion.create("Ezreal");

        System.out.println("ryze object 비교 : " + (ryze1.equals(ryze2)));
        System.out.println("ezreal object 비교 : " + (ezreal1.equals(ezreal2)));
    }
}

class Champion {
    private static class LazyHolder {
        private static final Ryze RYZE = new Ryze(575, 300, 50, 100);
        private static final Ezreal EZREAL = new Ezreal(500, 375, 30, 90);
    }

    // 하위 자료형 객체를 반환할 수 있다.
    public static ChampionModel create(String championName) {
        if (championName.equals("Rize")) {
            return LazyHolder.RYZE;
        }
        else if (championName.equals("Ezreal")) {
            return LazyHolder.EZREAL;
        }
        return null;
    }
}

interface ChampionModel {
    public void userQ();

    public void userR();
}

class Ryze implements ChampionModel {
    private int healthPoint;
    private int magicPoint;
    private int qPay;
    private int rPay;

    public Ryze(int healthPoint, int magicPoint, int qPay, int rPay) {
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