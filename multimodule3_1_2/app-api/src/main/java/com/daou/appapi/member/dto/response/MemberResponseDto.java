package com.daou.appapi.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class MemberResponseDto {
    private String name;

    public MemberResponseDto(String name) {
        this.name = name;
    }
}
