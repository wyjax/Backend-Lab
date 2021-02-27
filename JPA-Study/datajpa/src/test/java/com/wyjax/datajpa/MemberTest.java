package com.wyjax.datajpa;

import com.wyjax.datajpa.member.domain.Member;
import com.wyjax.datajpa.team.doamin.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        Team teamA = new Team("A");
        Team teamB = new Team("B");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 28, teamA);
        Member member2 = new Member("member2", 32, teamB);
        Member member3 = new Member("member3", 28, teamA);
        Member member4 = new Member("member4", 32, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); // flush는 강제로 db에 쿼리를 날린다.
        em.clear(); // 청소

        List<Member> members = em.createQuery("select m from Member  m", Member.class)
                .getResultList();

        /* Member Team 연관관계가 LAZY로 되어 있어서 Member에서 getTeam을 하게 되면 그때서야 team쿼리가 발생
            select
                member0_.member_id as member_i1_0_,
                member0_.age as age2_0_,
                member0_.team_id as team_id4_0_,
                member0_.username as username3_0_
            from
                member member0_
        Member(id=9, username=member1, age=28)
        Hibernate:
            select
                team0_.team_id as team_id1_1_0_,
                team0_.name as name2_1_0_
            from
                team team0_
            where
                team0_.team_id=?
        Team(id=5, name=A)
         */
        // fetchJoin, entityGraph를 이용해서 위와같은 문제를 해결할 수 있다.
        for(Member member : members) {
            System.out.println(member);
            System.out.println(member.getTeam());
        }
    }
}
