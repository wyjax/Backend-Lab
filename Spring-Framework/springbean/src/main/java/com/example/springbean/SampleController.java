package com.example.springbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    // Autowired를 사용하여 생성한 Bean에 대해서 의존성을 주입받는다.
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ConfigurationBean configurationBean;

    @Autowired
    private Person person;

    @GetMapping("/bean1")
    public String bean1() {
        // Bean은 따로 명명을 하지 않으면 class or method의 camelCase로 설정된다.
        return applicationContext.getBean("configurationBean").toString();
    }

    @GetMapping("/bean2")
    public String bean2() {
        return applicationContext.getBean("beanSample").toString();
    }

    @GetMapping("/bean3")
    public String bean3() {
        return applicationContext.getBean("person").toString();
    }
}
