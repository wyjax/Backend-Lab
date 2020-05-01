import javax.annotation.PreDestroy;

/**
 *  > Singleton 패턴 ?
 *      애플리케이션이 시작할 때 싱글턴 패턴으로 되어있는 클래스가 최초 한 번만 메모리를 할당하고
 *      그 메모리에 인스턴스를 만들어 사용하는 디자인 패턴
 *
 *  > 사용 이유 ?
 *      도정된 메모리 영역을 사용하면서 한 번의 new로 인스턴스를 사용하기 때문에 메모리의 낭비를
 *      방지할 수 있다. 또한 싱글턴으로 만들어진 클래스 인스턴스는 전역변수 이기 때문에 데이터를
 *      공유하기 싫다.
 *
 *  > 문제점 ?
 *      싱글톤 인스턴스가 너무 많은 일을 하거나 많은 데이터를 공유할 경우 다른 클래스 인스턴스와 결합도가
 *      높아져 개방-폐쇄 원익을 위배할 수도 있다.
 */

// 방법 1 : Thread safe Lazy initialization
class Singleton {
    private static Singleton singleton;

    private Singleton() {
        System.out.println("방법 1 생성자");
    }

    /**
     * 이렇게 사용하면 인스턴스가 있건 없건 동기화 검사를 함으로 성능저하가 있다.
     */
    public static synchronized Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}

// 방법 2 : Thread safe lazy initialization + Double-checked locking
class SingletonDoubleChecking {
    private static SingletonDoubleChecking singletonDoubleChecking;

    private SingletonDoubleChecking() {
        System.out.println("방법 2 생성자");
    }

    /**
     * 방법 1의 성능저하를 개선 시킨 방법 > 완벽한 방법은 아님
     */
    public static SingletonDoubleChecking getInstance() {
        if (singletonDoubleChecking == null) {
            synchronized (SingletonDoubleChecking.class) {
                if (singletonDoubleChecking == null) {
                    singletonDoubleChecking = new SingletonDoubleChecking();
                }
            }
        }
        return singletonDoubleChecking;
    }
}

class SingletonLazyHolder {
    private SingletonLazyHolder() {
        System.out.println("방법 3 생성자");
    }

    /**
     * JVM의 Class Loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
     * >> 가장 많이 사용되는 방법
     */
    private static class LazyHolder {
        private static final SingletonLazyHolder SINGLETON_LAZY_HOLDER = new SingletonLazyHolder();
    }

    public static SingletonLazyHolder getInstance() {
        return LazyHolder.SINGLETON_LAZY_HOLDER;
    }
}

public class Main {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        SingletonDoubleChecking singletonDoubleChecking = SingletonDoubleChecking.getInstance();
        SingletonLazyHolder singletonLazyHolder = SingletonLazyHolder.getInstance();

        for (int i = 0; i < 5; i++) {
            singleton = Singleton.getInstance();
        }
        for (int i = 0; i < 5; i++) {
            singleton = Singleton.getInstance();
        }
        for (int i = 0; i < 5; i++) {
            singleton = Singleton.getInstance();
        }
    }
}
