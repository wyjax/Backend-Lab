
/**
 * daemon thread는 main thread가 종료되면 연쇄적으로 종료되는 thread를 말한다.
 */
public class ThreadDaemon {
    public static void main(String[] args) throws InterruptedException {
        Thread th1 = new Thread(new TestThread());
        // main thread죽을시 종료
        th1.setDaemon(true);
        th1.start();

        /*
            default priority == Thread.NORM_PRIORITY
            output : 5
         */
        System.out.println(th1.getPriority());

        // main thread는 500ms 만큼 thraed의 작업을 기다려줌
        Thread.sleep(500);

        // 종료시 th1 스레드 함께 종료
        System.out.println("main Exit");
    }
}

class TestThread implements Runnable {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(80);
                System.out.println(Thread.currentThread().getName() + " call : " + i);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}