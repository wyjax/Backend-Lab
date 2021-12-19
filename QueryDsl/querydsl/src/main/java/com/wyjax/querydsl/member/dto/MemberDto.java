package com.wyjax.querydsl.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"name", "age"})
public class MemberDto {
    private String name;
    private int age;

    public MemberDto(String name) {
        this.name = name;
    }

    public MemberDto(String name, int age) {
        this.name = name + " 님";
        this.age = age;
    }
}
