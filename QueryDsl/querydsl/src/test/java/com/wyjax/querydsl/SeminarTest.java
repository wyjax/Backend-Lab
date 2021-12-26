package com.wyjax.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.menu.domain.Menu;
import com.wyjax.querydsl.store.domain.Store;
import com.wyjax.querydsl.store.query.StoreQuery;
import com.wyjax.querydsl.store.query.StoreSpecification;
import com.wyjax.querydsl.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class SeminarTest {
    private static final String[] menus = {"낙곱새", "낙차새", "낙삼새"};
    private static final long[] prices = {11000, 11000, 11000};

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @Autowired
    StoreQuery storeQuery;
    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Store store = Store.builder()
                .name("미스터 낙곱새")
                .address("용인시")
                .officeNumber("031-728-2232")
                .ownerName("엄정기")
                .build();
        initMenuGroups(store);
        em.persist(store);
        em.flush();
        em.clear();
    }

    private void initMenuGroups(Store store) {
        for (int i = 0; i < menus.length; i++) {
            Menu menu = new Menu(menus[i], prices[i], store);
            store.addMenu(menu);
        }
    }

    @Test
    public void store_조회() {
        String searchName = "미스터 낙곱새";
        Store store = storeRepository.findStore(searchName);
        Assertions.assertThat(store.getName()).isEqualTo(searchName);
    }

    @Test
    public void store_조회_querydsl() {
        String name = "미스터 낙곱새";
        Store store = storeQuery.findStoreByQuerydsl(name);

        Assertions.assertThat(store.getName()).isEqualTo(name);
    }

    @Test
    public void store_조회_spectification() {
        String name = "미스터 낙곱새";
        Store store = storeRepository.findAll(StoreSpecification.nameEq(name)).get(0);

        Assertions.assertThat(store.getName()).isEqualTo(name);
    }

    @Test
    public void store_조회_메뉴로_찾기() {
        String name = "미스터 낙곱새";
        String menuName = "낙곱새";
        Store store = storeQuery.findStoreByMenuName(menuName);
        Assertions.assertThat(store).isNotNull();
        Assertions.assertThat(store.getName()).isEqualTo(name);
    }
}
