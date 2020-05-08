package com.example.springaop.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 메소드에 붙일 것이기 때문에 Target을  method로 해준다.
@Target(ElementType.METHOD)
// RetentionPolicy는 애노테이션을 사용하는 코드를 언제까지 유지할건지 설정 > RUNTIME까지 설정
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectAround {

}
