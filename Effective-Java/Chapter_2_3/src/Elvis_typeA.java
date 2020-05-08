/**
 * public static final 인스턴스 생성
 * ! 리플렉션 방어 안됨
 */
public class Elvis_typeA {
    public static final Elvis_typeA INSTANCE = new Elvis_typeA();

    private Elvis_typeA() {}

    public void good() {
        System.out.println("Elvis_typeA 싱글턴");
    }

    // 싱글턴임을 보장해주는 readResolve() 메서드 / 역직렬화할 때 가짜 인스터스가 생성되는 걸 막는다.
    private Object readResolve() {
        return INSTANCE;
    }
}
