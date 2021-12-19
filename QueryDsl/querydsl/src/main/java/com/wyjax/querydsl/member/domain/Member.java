package com.wyjax.querydsl.member.domain;

import com.wyjax.querydsl.team.domain.Team;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "age"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Member(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
    }
}
