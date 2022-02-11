package com.wyjax.querydsl.store.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.store.domain.Store;
import com.wyjax.querydsl.store.dto.QStoreDto;
import com.wyjax.querydsl.store.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select s from Store s wher e 1 = 1");

        if (StringUtils.hasText(name)) {
            queryBuilder.append(" and name = :name");
        }
        TypedQuery<Store> query = em.createQuery(queryBuilder.toString(), Store.class);

        if (StringUtils.hasText(name)) {
            query.setParameter("name", name);
        }
        return query.getSingleResult();
    }

    public Store findStoreByMenuName(String menuName) {
        return queryFactory.select(store)
                .from(store)
                .join(store.menus, menu)
                .where(menuNameEq(menuName))
                .fetchFirst();
    }

    public Store findStoreByMenuName_nonMapping(String menuName) {
        return queryFactory.select(store)
                .from(store)
                .join(menu).on(menu.store.eq(store))
                .join(store.menus, menu)
                .where(menuNameEq(menuName))
                .fetchFirst();
    }

    private BooleanExpression menuNameEq(String menuName) {
        if (StringUtils.hasText(menuName)) {
            return menu.name.eq(menuName);
        }
        return null;
    }

    public StoreDto getStoreDtos_bean(String name) {
        return queryFactory.select(Projections.bean(StoreDto.class,
                store.name,
                menu.name.as("menuName")))
                .from(store)
                .innerJoin(store.menus, menu)
                .where(store.name.eq(name))
                .fetchFirst();
    }

    public StoreDto getStoreDtos_Fields(String name) {
        return queryFactory.select(Projections.fields(StoreDto.class,
                menu.name.as("menuName"),
                store.name))
                .from(store)
                .innerJoin(store.menus, menu)
                .where(store.name.eq(name))
                .fetchFirst();
    }

    public StoreDto getStoreDtos_Constructor(String name) {
        return queryFactory.select(Projections.constructor(StoreDto.class,
                store.name, menu.name))
                .from(store)
                .innerJoin(store.menus, menu)
                .where(store.name.eq(name))
                .fetchFirst();
    }

    public StoreDto getStoreDtos_queryProjection(String name) {
        return queryFactory.select(new QStoreDto(store.name, menu.name))
                .from(store)
                .innerJoin(store.menus, menu)
                .where(store.name.eq(name))
                .fetchFirst();
    }
}
