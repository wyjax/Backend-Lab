## 스프링 프레임워크 입문

### Inversion of Control (제어의 역전)

아무거나 뒤 바뀔수 있지만 여기서 말하는 것은 주로 **'의존성'** 이다.

```Java
class OwnerController {
	// 스프링을 사용하기 IoC를 사용하지 않으면 이렇게 해서 의존성을 설정해줘야 한다.
	private OwnerRepository repository = new OwnerRepository();
}

class OwnerControllerTest {
	OwnerRepository repo = new OwnerRepository();
	// 여기에서 Controller에 OwnerRepository의 의존성을 주입해준다.
	OwnerController controller = new OwnerController(repo);
}
```

> - 의존성을 내가 관리하지 않고 나 이외에 누군가가 의존성을 넣어주는 것을 **'ioc'** 라고 한다.
>
> - IoC 컨테이너가 클래스에 대한 의존성을 가지고 있다가 필요할 때 사용하는 곳에서 사용할 것의 의존성을 주입하는 것

### IoC 컨테이너

> IoC용으로 컨테이너를 제공해주고 핵심적인 것이 ApplicationContext & BeanFactory
>
> **IoC 컨테이너라는게 있다. 이 친구가 Bean을 만들고 의존성을 엮어준다.**
>
> > 의존성을 할 때는 @Autowired를 사용하면 된다.
>
> IoC 컨테이너는 직접 쓸 일은 많지 않다.

### Bean

```
Bean은 IoC 컨테이너가 관리하는 객체

@ 빈을 등록할 떄 (name, value를 설정하지 않으면 class나 method 이름이 camelCase로 들어간다)

- 등록하는 방법 2가지
	1. Component Scanning
		- 등록된 패키지 안에 @Component를 스캔해서 Bean으로 등록한다.
			> 개발자가 직접 작성한 class를 Bean으로 등록하기 위한 어노테이션
	2. 직접 xml이나 자바 설정 파일에 등록
		2-1 @Bean 어노테이션을 활용하여 등록
		2-2 @Bean을 하려면 @configuration 어노테이션 안에서 해야 한다.
		> bean만 bean을 쓸 수 있다. (class 안에 @bean이 있는 경우 class도 @bean으로 등록해줘야 한다.)
	
@Component 를 붙이면 Component scan에 의해서 Bean으로 등록이 된다.
	> Boot가 아닌 버전에서는 @component를 등록한 다음에 ioc 컨테이너에게 이러한 어노테이션이 있다는 것을 알려주기 위해
	  context.xml 파일에 component-scan을 추가해줘서 찾을 수 있도록 해준다.


- 다형성을 뛰고 있는 객체 타입에 @Autowired를 사용한 경우에는 @Qualifier("bean이름")을 사용하여 해당 자리에 주입될 Bean을 명시해주어야 한다.
```



어노테이션 기능은 없다 주석같은 존재다

- 어노테이션 자체는 기능은 없다. Annotation 프로세서가 있다.