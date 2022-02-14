package com.wyjax.amqpstudy.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping("/api/order")
    public Long order() {

        return null;
    }
}
