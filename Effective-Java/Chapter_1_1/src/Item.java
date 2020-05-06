class Person {
    private static Person person = new Person();
    private String name = "default";

    // 기본 private 생성자
    private Person() {

    }

    // name 초기화 private 생성자
    private Person(String name) {
        this.name = name;
    }

    public static Person getInstance() {
        return person;
    }

    public synchronized static Person newInstance() {
        person = new Person();
        return person;
    }

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