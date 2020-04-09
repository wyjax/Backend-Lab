package com.wyjax.oauth2jwt.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uid;

    @Column
    private String name;

    @Column
    private String serverName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String picture;

    @Builder
    public Member(String uid,
                  String name,
                  String picture,
                  String serverName,
                  Role role) {
        this.uid = uid;
        this.name = name;
        this.picture = picture;
        this.role = role;
        this.serverName = serverName;
    }

    public Member update(String picture) {
        this.picture = picture;
        return this;
    }

    public String getRole() {
        return this.role.getKey();
    }
}
