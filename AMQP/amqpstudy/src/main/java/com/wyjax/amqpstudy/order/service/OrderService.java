package com.wyjax.amqpstudy.order.service;

import com.wyjax.amqpstudy.order.dto.OrderRequestDto;
import com.wyjax.amqpstudy.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrder(OrderRequestDto requestDto) {

    }
}
