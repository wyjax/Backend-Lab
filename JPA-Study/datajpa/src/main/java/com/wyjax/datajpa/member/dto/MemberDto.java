package com.wyjax.datajpa.member.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDto {
    private String name;
    private int age;

    public MemberDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
