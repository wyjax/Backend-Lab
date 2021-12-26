package com.wyjax.querydsl.menu.domain;

import com.wyjax.querydsl.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(String name, long price, Store store) {
        this.name = name;
        this.price = price;
        this.store = store;
    }
}
/*
    - querydsl 을 사용하는 이유
    - querydsl vs jpql vs specification vs native query
    - 편리했던 점
        - jpql에서 사용할 수 있는 eq, ne, gt, lt와 같은 용어를 사용할 수 있다.
 */