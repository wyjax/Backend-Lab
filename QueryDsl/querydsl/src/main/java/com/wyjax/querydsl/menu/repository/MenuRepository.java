package com.wyjax.querydsl.menu.repository;

import com.wyjax.querydsl.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
