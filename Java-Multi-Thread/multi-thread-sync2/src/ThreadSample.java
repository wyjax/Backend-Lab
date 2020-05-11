public class ThreadSample {
    static int val = 0;
    static final int addVal = 1;

    /**
     *  Instance 메서드 동기화 : instance 별로 동기화를 이루게 됩니다.
     *  >> 인스턴스 메서드의 동기화는 이 메서드를 가진 인스턴스 별로 동기화가 된다.
     */
    public synchronized void instanceMethodSync() {
        val += addVal;
    }

    /**
     *  Static 메소드 동기화
     *  >> static method를 동기화하면 class 별로 동기화 된다.
     */
    public static synchronized void staticMethodSync() {
        val += addVal;
    }

    // Instance 메소드 Sync 블록
    public void instanceSyncBlock() {
        /*
            sync block을 통해서 필요한 부분만 동기화 처리
            this 객체를 전달해 자기자신을 모니터 객체로 전달하고 있다.
            위의 instance Method와 같은 역할을 수행
         */
        // 모니터 객체 : this >> 모니터 객체란? synchronized block에 전달되는 객체
        synchronized (this) {
            val += addVal;
        }
    }

    // Static 메소드 Sync 블록
    public void staticSyncBlock() {
        synchronized (ThreadSample.class) {
            val += addVal;
        }
    }
}
