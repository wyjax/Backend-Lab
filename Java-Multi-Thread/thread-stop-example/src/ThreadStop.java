public class ThreadStop {
    /**
     * 기존의 thread의 stop() 함수는 depreciated 되었다.
     */

    public static void main(String[] args) throws InterruptedException {
        int deadTime = 1000;
        int currentTime = 0;
        int incrementTimeValue = 120;

        ThreadTest thread = new ThreadTest();
        thread.start();

        while (currentTime <= deadTime) {
            Thread.sleep(incrementTimeValue);
            currentTime += incrementTimeValue;

            if (!thread.isAlive()) {
                break;
            }
        }

        if (thread.isAlive()) {
            thread.threadStop();
            // thread가 종료될 때까지 wait 하게 된다.
            thread.join();
        }

        System.out.println("main 종료");
    }
}

class ThreadTest extends Thread {
    private boolean isBreak = false;

    public void threadStop() {
        isBreak = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
                if (isBreak) {
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("thead done");
    }
}
