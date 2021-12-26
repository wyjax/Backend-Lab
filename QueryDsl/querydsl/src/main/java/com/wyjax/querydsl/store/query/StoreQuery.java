package com.wyjax.querydsl.store.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wyjax.querydsl.menu.domain.QMenu.menu;
import static com.wyjax.querydsl.store.domain.QStore.store;

@Component
@RequiredArgsConstructor
public class StoreQuery {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Store findStoreByQuerydsl(String name) {
        return queryFactory.selectFrom(store)
                .where(nameEq(name))
                .fetchFirst();
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : store.name.eq(name);
    }

    public Store findStoreByJpql(String name) {
        List<Store> store = em.createQuery("select s from Store s")
                .getResultList();
        return store.get(0);
    }

    public Store findStoreByMenuName(String menuName) {
        return queryFactory.select(store)
                .from(store)
                .join(store.menus, menu)
                .where(menu.name.eq(menuName))
                .fetchFirst();
    }
}
