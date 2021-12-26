package com.wyjax.querydsl.store.query;

import com.wyjax.querydsl.store.domain.Store;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpecification {

    public static Specification<Store> nameEq(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("good"), name);
    }
}


