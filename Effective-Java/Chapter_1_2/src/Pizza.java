import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * 빌더 패턴은 계층적으로 설계된 클래스와 함꼐 쓰기에 좋다. 각 계층의 클래스에 관련 빌더를
 * 멤버로 정의 > 추상 클래스는 추상 빌더를, 구체 클래스는 구체 빌더를 갖게 한다.
 * <p>
 * Pizza.Builder 클래스는 재귀적 타입 한정을 이용하는 제네릭 타입이다.
 *
 * >> 빌더의 단점
 *      빌더 생성 비용이 크지는 않지만 성능에 민감한 상황에서는 문재가 될 수 있다. 또한 점층적
 *      생성자 패턴보다는 코드가 장황해서 매개변수가 4개 이상은 되어야 값어치를 한다.
 *      !! 하지만 매개변수는 시간이 지날수록 많아질 것을 명심하자 !!
 *
 *      생성자나 정적 팩터리 방식으로 시작했다가 나중에 매개변수가 많아지면 빌더 패턴으로
 *      전환할수도 있지만, 이전에 만들어둔 생성자와 정적 팩터리가 도드라져 보일 것이다.
 *      >> 그렇기 때문에 애초에 빌더 패턴으로 시작하는 것도 좋다.
 *
 *  < 정리 >
 *   생성자나 정적 팩터리가 처리해야 할 매개변수가 많으면 빌더 패턴을 선택하는게 낫다.
 *   매개 변수중 다수가 필수가 아니거나 같은 타입이면 특히 더 그럼
 *
 *   빌더의 장점 > 점층적 생성자보다 클라이언트 코드를 읽기가 용이하고 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.
 */

// 구체 클래스는 구체 빌더를 가진다.
class NyPizza extends Pizza {
    public enum Size {SMALL, MEDIUM, LARGE}

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}

// 추상 클래스는 추상 빌더를 가진다.
public abstract class Pizza {
    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

    final Set<Topping> toppings;

    //                            Builder extends ( Pizza.Builder<Builder> )
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));

            return self();
        }

        /*
            하위 클래스는 이 메서드를 오버라이딩하여
            'this' 를 반환하도록 해야 한다.
         */
        abstract Pizza build();

        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}
