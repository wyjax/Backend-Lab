package com.wyjax.amqpstudy.order.repository;

import com.wyjax.amqpstudy.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
