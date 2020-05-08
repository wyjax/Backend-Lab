/**
 * 정적 팩터리 메소드 방식으로 싱글턴 구현
 * ! 리플렉션 방어 안됨
 */
public class Elvis_typeB {
    // private로 선언하고 getInstance()로 인스턴스 반환 > 팩터리 메서드
    private static final Elvis_typeB INSTANCE = new Elvis_typeB();

    private Elvis_typeB() {}

    public static Elvis_typeB getInstance() {
        return INSTANCE;
    }

    public void good() {
        System.out.println("Elvis_typeB 싱글턴");
    }

    // 싱글턴임을 보장해주는 readResolve() 메서드 / 역직렬화할 때 가짜 인스터스가 생성되는 걸 막는다.
    private Object readResolve() {
        return INSTANCE;
    }
}
