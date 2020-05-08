package com.example.springaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

// Aspect를 처리할 클래스 (실제 aspect)
// 스프링 Bean만 aspect가 될 수 있다.

// LogExcutionTime을 구현
@Component
@Aspect
public class LogAspect {

    /*
        Aspect 주석
        - @Before - 메소드 실행 전에 실행
        - @After - 메소드가 결과를 리턴한 후 실행
        - @AfterReturning - 메소드가 결과를 리턴한 후 실행하고 리턴된 결과도 인터셉트한다.
        - @AfterThrowing - 메소드에서 예외가 발생한 후에 실행
        - @Around - 메소드 실행하고 3가지 조건을 모두 실행
     */

    // 전 후로 실행
    @Around("@annotation(aop.AspectAround)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around Before");

        Object ret = joinPoint.proceed();

        System.out.println("@Around After");

        return ret;
    }

    // void로 해야함
    @Before("@annotation(aop.AspectBefore)")
    public void Aopbefore(JoinPoint joinPoint) {
        System.out.println("AOP @Before : " + joinPoint.getSignature());
    }

    /*
        - @AfterReturning로 aop를 설정하고 returning을 설정했다면 aop를 설정한 메소드가
          returning에 맞는 반환값을 반환해야 aop가 동작된다.
          >> 반환값 조건을 만족하지 않으면 aop 실행안됨
     */
    // @@After(value = "@annotation(LogExcutionTime3)")
    @AfterReturning(value = "@annotation(aop.AspectAfter)", returning = "str")
    public void Aopafter(JoinPoint joinPoint, String str) {
        System.out.println("AOP @After : " + joinPoint.getSignature());
        System.out.println(str);
    }
}
