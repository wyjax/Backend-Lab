package com.example.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    // 생성자 방식
    private final ApplicationContext applicationContext;
    private final BeanSample1 beanSample1;
    // setter를 통한 DI
    private BeanSample3 beanSample3;

    // @Autowired를 통한 DI
    @Autowired
    private BeanSample2 beanSample2;

    // 생성자를 통한 DI
    public HomeController(BeanSample1 beanSample1, ApplicationContext applicationContext) {
        this.beanSample1 = beanSample1;
        this.applicationContext = applicationContext;
    }

    @Autowired
    private void setBeanSample3(BeanSample3 beanSample3) {
        this.beanSample3 = beanSample3;
    }

    @GetMapping("bean1")
    public String bean1() {
        return applicationContext.getBean(BeanSample1.class).toString();
    }

    @GetMapping("bean2")
    public String bean2() {
        return applicationContext.getBean(BeanSample2.class).toString();
    }

    @GetMapping("bean3")
    public String bean3() {
        return applicationContext.getBean(BeanSample3.class).toString();
    }
}
