package com.wyjax.datajpa.repository;

import com.wyjax.datajpa.member.domain.Member;
import com.wyjax.datajpa.member.repository.MemberRepository;
import com.wyjax.datajpa.team.doamin.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        List<Member> members = memberRepository.findUser("member1", 28);

        Assertions.assertThat(members.get(0)).isEqualTo(member1);
    }
}
