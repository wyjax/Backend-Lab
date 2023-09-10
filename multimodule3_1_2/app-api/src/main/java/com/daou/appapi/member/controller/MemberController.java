package com.daou.appapi.member.controller;

import com.daou.appapi.member.dto.response.MemberResponseDto;
import com.daou.appapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/member")
    public ResponseEntity<MemberResponseDto> getMember() {
        MemberResponseDto result = memberService.getMember();
        return ResponseEntity.ok(result);
    }
}
