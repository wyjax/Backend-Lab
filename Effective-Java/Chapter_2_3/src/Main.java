/**
 * private 생성자나 열거 타입으로 싱글턴임을 보증하라
 */
public class Main {
    public static void main(String[] args) {
        Elvis_typeA a = Elvis_typeA.INSTANCE;
        Elvis_typeB b = Elvis_typeB.getInstance();
        Elvis_typeC c = Elvis_typeC.INSTANCE;

        a.good();
        b.good();
        c.good();

        System.out.println(a.equals(Elvis_typeA.INSTANCE));
        System.out.println(b.equals(Elvis_typeB.getInstance()));
        System.out.println(c.equals(Elvis_typeC.INSTANCE));
    }
}
