public class UsingThread {
    public static void main(String[] args) throws InterruptedException {
        Calculator calculator = new Calculator(1000000000L);

        Thread th1 = new Thread(calculator);
        // thread 이름 설정
        th1.setName("MAX_PRIORITY");
        // thread 우선순위 설정
        th1.setPriority(10);

        Thread th3 = new Thread(calculator);
        th3.setName("MIN_PRIORITY");
        th3.setPriority(Thread.MIN_PRIORITY);

        Thread th2 = new Thread(calculator);
        th2.setName("NORM_PRIORITY");
        th2.setPriority(Thread.NORM_PRIORITY);

        // thread 시작
        th1.start();
        th2.start();
        th3.start();

        // thread 종류 후에 main이 종료되도록 join을 걸음
        // 매개변수 값은 mills 시간 까지만 thread를 기다린다. mills 지나면 자동종료
        th1.join(10000);
        th2.join(10000);
        th3.join(10000);

        System.out.println("main exit");
    }
}

/**
 * multi-thread들의 우선순위 할당을 통해서 한정되어 있는 CPU자원을 thread 별로 분배할 수 있다.
 */

class Calculator implements Runnable {
    long value;

    public Calculator(long value) {
        this.value = value;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long sum = 0;

        for (int i = 0; i < value; i++) {
            sum++;
        }
        // running time = 종료 - 시작
        long runningTime = System.currentTimeMillis() - startTime;
        System.out.println("스레디 : " + Thread.currentThread().getName() + " RunningTime : " + runningTime);
    }
}
