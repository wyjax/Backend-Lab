package com.example.springaop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SampleController {
    private final ComponentBean componentBean;

    @Autowired
    private String good;

    /**
     * 실행순서
     *  1. @Around Before
     *  2. @Before
     *  3. @Around After
     *  4. @After
     */
    @LogExcutionTime2 // @Before
    @LogExcutionTime // Around
    @LogExcutionTime3 // @After
    @GetMapping("/api")
    public String api() {

        componentBean.prints();
        return "hello" + good;
    }

    @LogExcutionTime3
    @GetMapping("/api2")
    public String api2() {
        return "api2";
    }

    // AOP3는 반환값이 string으로 줘야 실행되기 때문에 int를 반환하면 aop가 실행되지 않는다.
    @LogExcutionTime3
    @GetMapping("/api3")
    public Integer api3() {
        System.out.println("api3 실행");
        // aop 실행안됨
        return 1;
    }
}
