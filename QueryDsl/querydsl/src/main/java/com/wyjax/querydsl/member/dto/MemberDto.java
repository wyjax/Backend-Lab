package com.wyjax.querydsl.member.dto;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public MemberDto(String name) {
        this.name = name + " 살";
    }

    @QueryProjection
    public MemberDto(String name, int age) {
        this.name = name + " 님";
        this.age = age;
    }
}
