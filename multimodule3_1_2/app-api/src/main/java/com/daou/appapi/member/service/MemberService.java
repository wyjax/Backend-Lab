package com.daou.appapi.member.service;

import com.daou.appapi.member.dto.response.MemberResponseDto;
import com.daou.appcore.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Transactional(readOnly = true)
    public MemberResponseDto getMember() {
        Member member = Member.builder()
            .name("테슬라주주 엄정기")
            .build();
        return new MemberResponseDto(member.getName());
    }
}
