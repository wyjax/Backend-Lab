package com.wyjax.datajpa.repository;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import com.wyjax.datajpa.member.domain.Member;
import com.wyjax.datajpa.member.dto.MemberDto;
import com.wyjax.datajpa.member.repository.MemberRepository;
import com.wyjax.datajpa.team.doamin.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember() {
        Team teamA = new Team("A");
        Team teamB = new Team("B");
        Member member1 = new Member("member1", 28, teamA);
        Member member2 = new Member("member2", 32, teamB);
        Member member3 = new Member("member3", 28, teamA);
        Member member4 = new Member("member4", 32, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
    }

    @Test
    public void 나이테스트() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 32);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("member1", 25);

        Assertions.assertThat(members.get(0).getUsername()).isEqualTo(member1.getUsername());
        Assertions.assertThat(members.get(0).getAge()).isEqualTo(member1.getAge());
    }

    @Test
    public void HELLO테스트() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 32);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findByUsername("member1");

        Assertions.assertThat(members.size()).isEqualTo(1);
    }

    @Test
    public void 쿼리_테스트() {
        Member member1 = new Member("member1", 28);
        Member member2 = new Member("member2", 32);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findUser("member2321", 28);

        System.out.println(members.toString());
        Assertions.assertThat(members.get(0)).isEqualTo(member1);
    }

    // 페이징에서 total count가 느리게 될 수도 있다.
    @Test
    public void DTO쿼리_테스트() {
        Team teamA = new Team("A");
        Team teamB = new Team("B");
        Member member1 = new Member("member1", 28, teamA);
        Member member2 = new Member("member2", 32, teamB);
        Member member3 = new Member("member3", 28, teamA);
        Member member4 = new Member("member4", 32, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();
        memberDtos.forEach(memberDto -> System.out.println(memberDto));
    }

    @Test
    public void 페이징_테스트() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member123", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Member> members = memberRepository.findByAge(age, pageable);
        // then
        List<Member> members1 = members.getContent();
        long totalElements = members.getTotalElements();
    }
}
