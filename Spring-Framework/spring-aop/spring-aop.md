### Aspect Oriented Programming

> AOP의 핵심은 흩어진 코드를 한 곳에 모아라 !
>
> 흩어져있는 코드를 한 곳으로 모으는 코딩 기법이 >> AOP다



#### As-is 코드

```java
class A {
	method a () {
        AAAA;
        ㅎㅇ;
        BBBB;
    }
    
    method b () {
        AAAA;
        나다 wyjax;
        BBBB;
    }
}

class B {
    method c () {
        AAAA;
        dsdsdsd;
        BBBB;
    }
}
```

#### To-be 코드

```java
class A {
	method a () {
        ㅎㅇ;
    }
    
    method b () {
        나다 wyjax;
    }
}

class B {
    method c () {
        dsdsdsd;
    }
}

// Proxy 패턴, 스프링 AOP는 Proxy 패턴을 사용한다.
class AProxy extends A {
}

class AAAABBBB {
    method aaaabbbb() {
        AAAA;
//      point.execute();
        BBBB;    
    }
    
    method bbbb () {
        ....;
        BBBB;
    }
}
```



### AOP를 구현하는 기법은 크게 2가지

1. **바이트 코드 조작**

    컴파일하면 클래스파일이 생성되는데 클래스 파일을 조작하는 것이다.

    코드를 위에처럼 됐으면 동작은 코드를 끼워넣은 상태로 조작이 된다.

2. **프록시 패턴 (스프링 AOP)**

사방으로 흩어진 코드를 한 곳에 모으고 다른 기타 클래스들은 자신이 해야하는 일만 해야하도록 한다. > 객체지향으로 적합하게 코딩할 수 있도록 도와준다.



- Aspect 주석

    - @Before - 메소드 실행 전에 실행
    - @After - 메소드가 결과를 리턴한 후 실행
    - @AfterReturning - 메소드가 결과를 리턴한 후 실행하고 리턴된 결과도 인터셉트한다.
    - @AfterThrowing - 메소드에서 예외가 발생한 후에 실행
    - @Around - 메소드 실행하고 3가지 조건을 모두 실행

    

- @Around는 다 할 수 있다.

```java
@Around("@annotation(LogExcutionTime)")
```