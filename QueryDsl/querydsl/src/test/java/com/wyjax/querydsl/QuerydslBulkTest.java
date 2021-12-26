package com.wyjax.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.member.domain.Member;
import com.wyjax.querydsl.team.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.wyjax.querydsl.member.domain.QMember.member;

@SpringBootTest
@Transactional
public class QuerydslBulkTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("A");
        Team teamB = new Team("B");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        Member member3 = new Member("member3", 30, teamA);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void bulkUpdate() {
        long count = queryFactory
                .update(member)
                .set(member.name, "비회원")
                .where(member.age.lt(28))
                .execute();
    }

    @Test
    public void buldAdd() {
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .where(member.age.lt(28))
                .execute();

    }
}
