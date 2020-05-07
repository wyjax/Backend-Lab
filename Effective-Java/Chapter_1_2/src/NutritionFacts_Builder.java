public class NutritionFacts_Builder {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    /**
     * 잘못된 매개변수를 최대한 일찍 발견하려면 빌더의 생성자와 메소드에서 입력 매개변수를 검사하고
     * build 메서드가 호출하는 생성자에서 여러 매개변수에 걸친 불변식을 검사하자.
     */

    private NutritionFacts_Builder(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    public static class Builder {
        private final int servingSize;
        private final int servings;

        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        public NutritionFacts_Builder build() {
            return new NutritionFacts_Builder(this);
        }
    }
}
