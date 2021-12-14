package com.wyjax.querydsl.team.domain;

import com.wyjax.querydsl.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public void addMember(Member member) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        this.members.add(member);
    }
}
