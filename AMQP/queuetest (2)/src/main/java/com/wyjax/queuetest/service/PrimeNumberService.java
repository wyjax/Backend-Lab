package com.wyjax.queuetest.service;

import org.springframework.stereotype.Service;

@Service
public class PrimeNumberService {

    public void process(Long number) {
        for (int i = 2; i < number / 2; i++) {
            if (number % i == 0) {
                System.out.println("false");
                return;
            }
        }
        System.out.println("true");
    }
}
