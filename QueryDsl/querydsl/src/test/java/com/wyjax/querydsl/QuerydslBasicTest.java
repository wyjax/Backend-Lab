package com.wyjax.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.member.domain.Member;
import com.wyjax.querydsl.member.domain.QMember;
import com.wyjax.querydsl.member.dto.MemberDto;
import com.wyjax.querydsl.team.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wyjax.querydsl.member.domain.QMember.member;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        // 필드로 가져가도 좋다. 동시성 문제 없음 > 문제가 없게 설계가 되도록 되어 있다. 트랜잭션이
        // 어떻게 되어있느냐에 따라서 트랜잭션에 바인딩되어서 실행하도록 되어있다.
        Team teamA = new Team("A");
        Team teamB = new Team("B");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 199, teamA);
        Member member2 = new Member("member2", 100, teamB);
        em.persist(member1);
        em.persist(member2);
    }

    @Test
    public void simpleProjection() {
        List<String> result = queryFactory.select(member.name)
                .from(member)
                .fetch();
    }

    @Test
    public void tupleProjection() {
        /*
            tuple은 repository에서만 아는 것이 좋고 service, controller 로직으로 나가는 것은 좋지 않다.
            예를 들면 Optional 과 비슷한 맥락이다. >>> tuple 보다는 dto
         */
        // tuple로 가져옴
        List<Tuple> result = queryFactory
                .select(member.name, member, member.age)
                .from(member)
                .fetch();
    }

    @Test
    public void jpql_Projections_to_dto() {
        List<MemberDto> result = em
                .createQuery("select new com.wyjax.querydsl.member.dto.MemberDto(m.name, m.age) " +
                        "from Member m")
                .getResultList();
    }

    @Test
    public void dto_projection_bean() {
        // bean 은 getter, setter로 동작한다
        List<MemberDto> dtos = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();
        print(dtos);
    }

    @Test
    public void dtoFieldsProjection() {
        // getter, setter 필요 없음. 바로 값을 주입
        List<MemberDto> dtos = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.name.as("name"), // 별칭이 field와 같은 경우 생략가능
                        member.age.as("age")))
                .from(member)
                .fetch();
        print(dtos);
    }

    @Test
    public void findMemberDto() {
        // subquery 같은 경우는 아래와 같이 해야함
        QMember subMember = new QMember("subMember");
        queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.name.as("name"),
                        ExpressionUtils.as(JPAExpressions
                                .select(subMember.age.max())
                                .from(subMember), "age")))
                .from(member)
                .fetch();
    }

    @Test
    public void dtoConstructorProjection() {
        List<MemberDto> dtos = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();
        print(dtos);
    }

    private void print(List<?> list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }
}
