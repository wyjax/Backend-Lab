package com.wyjax.querydsl.store.query;

import com.wyjax.querydsl.menu.domain.Menu;
import com.wyjax.querydsl.store.domain.Store;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class StoreSpecification {

    public static Specification<Store> nameEq(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("good"), name);
    }

    public static Specification<Store> joinMenu(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<Store, Menu> join = root.join("menus", JoinType.INNER);
            return criteriaBuilder.equal(join.get("name"), name);
        };
    }
}


