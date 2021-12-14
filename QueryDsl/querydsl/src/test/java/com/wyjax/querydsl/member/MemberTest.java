package com.wyjax.querydsl.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.member.domain.Member;
import com.wyjax.querydsl.member.domain.QMember;
import com.wyjax.querydsl.team.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class MemberTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory query;

    @BeforeEach
    public void before() {
        query = new JPAQueryFactory(em);
        // 필드로 가져가도 좋다. 동시성 문제 없음 > 문제가 없게 설계가 되도록 되어 있다. 트랜잭션이
        // 어떻게 되어있느냐에 따라서 트랜잭션에 바인딩되어서 실행하도록 되어있다.
        Team teamA = new Team("A");
        Team teamB = new Team("B");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", teamA);
        Member member2 = new Member("member2", teamB);
        em.persist(member1);
        em.persist(member2);
    }

    @Test
    public void testJPQL() {
        List<Member> members = em.createQuery("select m from Member m where m.name = :name")
                .setParameter("name", "member1")
                .getResultList();
        Assertions.assertThat(members.get(0).getName()).isEqualTo("member1");
    }
    // jpql은 query가 사용자가 실행하고 쿼리가 실행된 시점에 알 수 있게 되고, querydsl은 컴파일 시점에서 오류를 잡을 수 있다.
    // jpql과 다르게 파라메터 바인딩을 허지 않아도 jpa preparestatement로 자동으로 바인딩 한다

    @Test
    public void testQuerydsl() {
        QMember m = new QMember("m");
        Member member = query.select(m)
                .from(m)
                .where(m.name.eq("member1"))
                .fetchOne();
        Assertions.assertThat(member.getName()).isEqualTo("member1");
    }
}
