import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadSync {
    public static void main(String[] args) throws InterruptedException {
        List<Character> list = new ArrayList<>();
        // synchronized 블록 사용
        Thread th1 = new StringReverseThread(list, "AAAAA");
        Thread th2 = new StringReverseThread(list, "BBBBB");

        th1.start();
        th2.start();

        th1.join();
        th2.join();
        // shared lock 사용

        Thread th3 = new StringReverseThread2(list, "CCCCC");
        Thread th4 = new StringReverseThread2(list, "DDDDD");

        th3.start();
        th4.start();

        th3.join();
        th4.join();
    }
}

class StringReverseThread extends Thread {
    private List<Character> names;
    private String message;

    public StringReverseThread(List<Character> names, String message) {
        this.names = names;
        this.message = message;
    }

    @Override
    public void run() {
        // synchronized ( lock을 걸 대상을 매개변수로 준다 )
        synchronized (names) {
            for (int i = 0; i < message.length(); i++) {
                try {
                    names.add(message.charAt(i));
                    Thread.sleep(50);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(names.toString());
        }
    }
}

class StringReverseThread2 extends Thread {
    private List<Character> names;
    private String message;
    private static Lock lock = new ReentrantLock();

    public StringReverseThread2(List<Character> names, String message) {
        this.names = names;
        this.message = message;
    }

    @Override
    public void run() {
        // synchronized ( lock을 걸 대상을 매개변수로 준다 )
        lock.lock();

        for (int i = 0; i < message.length(); i++) {
            try {
                names.add(message.charAt(i));
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(names.toString());
        lock.unlock();
    }
}