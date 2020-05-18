
/*
    chapter 2-5 : 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

    - 의존 객체 주입 방법
        1. 생성자
        2. 정적 팩터리 메서드
        3. 빌더

    클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면
    싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.

    >> 생성자나 정적 팩터리 메서드로 사용할 자원을 넘겨주자
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Lexicon {

}

class SpellChecker {
    private final Lexicon dictionary;

    // 인스턴스를 생성할 떄 필요한 방식을 생성자로 자원을 넘겨주는 방식
    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        return true;
    }

    public List<String> suggestions(String type) {
        return new ArrayList<>();
    }
}

public class Main {
    public static void main(String[] args) {

    }
}
