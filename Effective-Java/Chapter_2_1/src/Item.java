class Person {
    private static Person person = new Person();
    private String name = "default";
    private int age = 0;

    private Person() {

    }

    // name을 초기화하는 생성자
    private Person(String name) {
        this.name = name;
    }

    // age로 초기화하는 생성자
    private Person(Integer age) {
        this.age = age;
    }

    private Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // from by name(string)
    public Person fromByName(String name) {
        return new Person(name);
    }

    // from by age(integer)
    public Person fromByAge(int age) {
        return new Person(age);
    }

    // of : 여러 매개변수로 생성
    public Person of(String name, int age) {
        return new Person(name, age);
    }

    public Person valueOf(String name, int age) {
        return new Person(name, age);
    }

    // 기존 인스턴스 return
    public static Person getInstance() {
        return person;
    }

    // new 인스턴스 return
    public synchronized static Person newInstance() {
        person = new Person();
        return person;
    }

    // 멀티 스테드 동기화
    public synchronized static Person newInstanceByName(String name) {
        person = new Person(name);
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}