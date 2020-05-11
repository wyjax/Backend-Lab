/**
 * synchronized keyword는 다음 4가지 유성의 block에서 사용된다.
 * <p>
 * 1. Instance Method
 * 2. Static Method
 * 3. Instance Method Code Block
 * 4. Static Method Code Block
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadSample threadSample = new ThreadSample();
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++)
//                    threadSample.instanceMethodSync();
//                    threadSample.staticMethodSync();
//                    threadSample.instanceSyncBlock();
                    threadSample.staticSyncBlock();
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++)
//                    threadSample.instanceMethodSync();
//                    threadSample.staticMethodSync();
//                    threadSample.instanceSyncBlock();
                    threadSample.staticSyncBlock();
            }
        });
        th1.start();
        th2.start();
        th1.join();
        th2.join();

        System.out.println(threadSample.val);
    }
}