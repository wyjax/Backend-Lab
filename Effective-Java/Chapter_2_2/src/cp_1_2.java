/*
    이펙티브 자바 chapter 1-2

    생성자에 매개변수가 많다면 빌더를 고려하라

    문제 ) 정적 팩터리 메서드와 생성자에는 똑같은 제약이 있다.
          >> 선택적 매개변수가 많을 때 적절히 대웅하기가 힘듦
    영양정보 1회 내용ㅇ량, 총 n회 제공량, 1회 제공량당 칼로리 같은 필수 항목 몇 개와
    총 지방, 트랜스지방, 포화지방 등 선택 항목

    >> 필수 항목 + 선택 항목

    - 점층적 생성자 패턴을 사용하면
    이런식으로 되어 있을 떄 조합으로 생성자를 만들면 생성자가 많아지고 코드가 더러워짐


    빌더 패턴은 유연하다. 빌더 하나로 여러 객체를 순회하면서 만들 수 있고, 빌더에
    넘기는 매개변수에 매개변수에 따라 다른 객체를 만들 수 있다.
 */

public class cp_1_2 {
    public static void main(String[] args) {
        NutritionFacts_Builder nutritionFacts_builder = new NutritionFacts_Builder.Builder(20, 20)
                .calories(20)
                .build();

        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.SAUSAGE).addTopping(Pizza.Topping.ONION).build();
    }
}

