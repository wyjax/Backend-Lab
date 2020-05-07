/**
 * JavaBeans Pattern
 *
 *      > 단점 : 1. 객체 하나를 만들려면 메소드를 여러 개 호출해야함
 *              2. 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태임
 *              3. 클래스를 불변 형태로 만들 수가 없음
 */
public class NutritionFacts_JavaBeansPattern {
    private int servingSize = -1;
    private int servings = -1;
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts_JavaBeansPattern() {

    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
