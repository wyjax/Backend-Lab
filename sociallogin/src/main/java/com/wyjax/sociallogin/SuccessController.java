package com.wyjax.sociallogin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessController {

    @GetMapping("/api/login/oauth2/code/kakao")
    public String good() {
        System.out.println("good");
        return "good";
    }
}