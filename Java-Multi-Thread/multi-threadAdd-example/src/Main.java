
/*
    - multi-threed 방식을 사용할 경우 thread-safe 하도록 프로그램을 작성
    - thread는 기본적으로 백그라운드 작업이 된다.
    - thread는 main이 종료되더라도 백그라운드에서 돌아간다.
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 10000; i++) {
                    c.increment();
                }
            }
        });

        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 10000; i++) {
                    c.increment();
                }
            }
        });
        t2.start();

        // wait until t1 & t2 thread is done
        // join을 사용하면 스레드가 종료되기는 기다렸다가 메서드가 종료된다.
        t1.join();
        t2.join();

        // print result
        System.out.println("t1.state=" + t1.getState() + "  t2.state=" + t2.getState());
        System.out.println("Result : " + c.count);
    }
}

class Counter {
    int count;

    // synchronized block
    public synchronized void increment() {
        count++;
    }
    // asynchronized block
    /*public void increment() {
        count++;
    }*/
}