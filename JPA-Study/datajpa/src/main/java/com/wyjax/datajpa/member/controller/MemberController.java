package com.wyjax.datajpa.member.controller;

import com.wyjax.datajpa.member.domain.Member;
import com.wyjax.datajpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public String findMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id).get();

        return member.getUsername();
    }

    // 도메인 컨버터 spring jpa가 찾아서 Member를 가져와준다.
    // 도메인 컨버터도 Repository로 엔티티를 찾는다. 딱 그냥 조회용으로 써야 한다.
    @GetMapping("/member2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @PostConstruct
    public void test() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}
