
class UtilityClass {
    /*
        명시적으로 기본생성자가 private라서 밖에서는 생성자에 접근할 수 없게 된다.

        - 이러한 방식은 어떠한 방식으로라도 클래스가 인스턴스화 되는 것을 막아준다.
        - 이 방식은 상속하는 것을 막아준다. 모든 생성자는 명시적이든 묵시적이든 상위
        클래스의 생성자를 호출하게 되는데, 이를 private으로 선언했으니 하위 클래스가
        상위 클래스의 생성자에 접근할 길이 막혀버린다.
     */

    // 기본 생성자가 만들어지는 것을 막는다(인스턴스화 방지용)
    private UtilityClass() {
        throw new AssertionError();
    }
}

//class childClass extends UtilityClass {
//
//}

public class Main {
    public static void main(String[] args) {
        /*
            인스턴스화를 막으려거든 private 생성자를 사용하라
         */
    }
}
