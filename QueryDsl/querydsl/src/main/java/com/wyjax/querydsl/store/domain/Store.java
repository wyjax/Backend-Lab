package com.wyjax.querydsl.store.domain;

import com.wyjax.querydsl.menu.domain.Menu;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "ownerName"})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String address;

    private String officeNumber;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Store(String name, String ownerName, String address, String officeNumber) {
        this.name = name;
        this.ownerName = ownerName;
        this.address = address;
        this.officeNumber = officeNumber;
    }

    public void addMenu(Menu menu) {
        if (menu == null) {
            throw new NullPointerException("menu가 null입니다.");
        }
        if (this.menus == null) {
            this.menus = new ArrayList<>();
        }
        this.menus.add(menu);
    }
}
