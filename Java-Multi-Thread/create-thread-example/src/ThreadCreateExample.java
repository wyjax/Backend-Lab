public class ThreadCreateExample {
    public static void main(String[] args) {
        Thread th1 = new Thread(new Thread1("thread 1"));
        Thread th2 = new Thread(new Thread2("thread 2"));

        // thread 시작
        th1.start();
        th2.start();
    }
}

// Thread class는 Runnable interface를 구현함
class Thread1 extends Thread {
    String name;

    public Thread1(String name) {
        this.name = name;
    }

    // run() method 오버라이딩
    @Override
    public void run() {
        System.out.println(name + " start");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(ThreadTime.time);
                System.out.println(name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " end");
    }
}

class Thread2 implements Runnable {
    String name;

    public Thread2(String name) {
        this.name = name;
    }

    // run() method 오버라이딩
    @Override
    public void run() {
        System.out.println(name + " start");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(ThreadTime.time);
                System.out.println(name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " end");
    }
}

class ThreadTime {
    static final int time = 100;
}