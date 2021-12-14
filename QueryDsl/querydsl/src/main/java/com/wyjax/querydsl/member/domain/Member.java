package com.wyjax.querydsl.member.domain;

import com.wyjax.querydsl.team.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
    }
}
