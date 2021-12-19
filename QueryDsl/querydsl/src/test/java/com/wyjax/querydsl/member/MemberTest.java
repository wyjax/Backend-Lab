package com.wyjax.querydsl.member;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wyjax.querydsl.member.domain.Member;
import com.wyjax.querydsl.member.domain.QMember;
import com.wyjax.querydsl.member.dto.MemberDto;
import com.wyjax.querydsl.team.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wyjax.querydsl.member.domain.QMember.member;
import static com.wyjax.querydsl.team.domain.QTeam.team;

@SpringBootTest
@Transactional
public class MemberTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory query;
//    @PersistenceContext
//    EntityManagerFactory emf;

    @BeforeEach
    public void before() {
        query = new JPAQueryFactory(em);
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
//        QMember qMember = QMember.member; // 기본 인스턴스 사용, 기본적으로 생성한 인스턴스가 있다
//        QMember m = new QMember("m"); > 같은 테이블을 조인해야 하는 경우
        // querydsl로 작성하면 jpql로 변환된다
        Member m = query.select(member)
                .from(member)
                .where(member.name.eq("member1"))
                .fetchOne();
        Assertions.assertThat(m.getName()).isEqualTo("member1");
    }
    /*
        Q type 생성방법
        QMember q = new QMember("q") // 별칭 직접 지정
        QMember qMember = QMember.member;
     */

    @Test
    public void search() {
        query.selectFrom(member)
                .where(member.name.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();
        // eq(equal), ne(not equal), in, notIn, between, goe, gt, loe, lt
    }

    @Test
    public void searchParams() {
        query.selectFrom(member)
                .where(
                        member.name.eq("member1"),
                        member.age.between(10, 30)
                ) // and인 경우 and()로 사용할 수 있지만 쉼표로도 사용가능함
                .fetchOne();
    }

    @Test
    public void resultFetch() {
        List<Member> members = query.selectFrom(member)
                .fetch();

        QueryResults<Member> memberResults = query.selectFrom(member)
                .fetchResults(); // 복잡한 쿼리가 되는 경우에는 성능이슈가 발생하기 때문에 쓰면안되고, 그 때는 쿼리를 따로 2번 날려야함
        List<Member> members2 = memberResults.getResults();
        memberResults.getTotal(); // member total 가져옴

        long count = query.selectFrom(member) // select count(id) from member;
                .fetchCount();

        Member mOne = query.selectFrom(member)
                .fetchOne(); // 결과가 둘 이상이면 exception 발생
        Member mFirst = query.selectFrom(member)
                .fetchFirst();
    }

    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member10", 100));

        List<Member> members = query.select(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.name.asc().nullsLast())
                .fetch();
        Member member5 = members.get(0);
        Member member10 = members.get(1);
        Member memberNull = members.get(2);
        Assertions.assertThat(member5.getName()).isEqualTo("member5");
        Assertions.assertThat(member10.getName()).isEqualTo("member10");
        Assertions.assertThat(memberNull.getName()).isNull();
    }

    @Test
    public void paging1() {
        List<Member> members = query.selectFrom(member)
                .orderBy(member.name.desc())
                .offset(0)
                .limit(2)
                .fetch();
        Assertions.assertThat(members.size()).isEqualTo(2);
    }

    @Test
    public void paging2() {
        QueryResults<Member> members = query.selectFrom(member)
                .orderBy(member.name.desc())
                .offset(0)
                .limit(2)
                .fetchResults();
    }

    @Test
    public void aggregation() {
        // tuple은 여러개 타입이 있을 경우 꺼내올 수 있음
        // 실제로 실무에서 튜플은 많이 사용해본 적이 없고, Projections 에서 dto로 뽑아서 사용함
        List<Tuple> result = query.select(
                member.count(),
                member.age.sum(),
                member.age.avg(),
                member.age.max(),
                member.age.min())
                .from(member)
                .fetch();
        Tuple tuple = result.get(0);
        Assertions.assertThat(tuple.get(member.count())).isEqualTo(2);
    }

    @Test
    public void group() throws Exception {
        List<Tuple> tuples = query.select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        Tuple teamA = tuples.get(0);
        Tuple teamB = tuples.get(1);

        Assertions.assertThat(teamA.get(team.name)).isEqualTo("B");
        Assertions.assertThat(teamB.get(team.name)).isEqualTo("A");
    }

    @Test
    public void join() {
        // 이렇게 할 경우 lazy 로딩으로 된다.
//        List<Member> result = query.selectFrom(member)
//                .fetch();
//        List<Member> result2 = query.selectFrom(member)
//                .join(member.team, team)
//                .where(team.name.eq("A"))
//                .fetch();
//        List<Member> leftResult = query.selectFrom(member)
//                .leftJoin(member.team, team)
//                .where(team.name.eq("A"))
//                .fetch();
//        List<Member> rightResult = query.selectFrom(member)
//                .rightJoin(member.team, team)
//                .where(team.name.eq("A"))
//                .fetch();
        // 크로스(세타) 조인 > 이러면 DB에서 다 가져와서 비교를 함, left, right join이 안되게됨
        query.select(member)
                .from(member, team)
                .where(member.name.eq(team.name))
                .fetch();
        List<Member> joinResult = query.select(member)
                .from(member)
                .join(team).on(team.eq(member.team))
                .fetch();
    }

    @Test
    public void join_on_filtering() {
//        List<Tuple> result = query.select(member, team)
//                .from(member)
//                .leftJoin(member.team, team).on(team.name.eq("A"))
//                .fetch();
//        for (Tuple tuple : result) {
//            System.out.println(tuple.toString());
//        }
//
//        List<Tuple> result2 = query.select(member, team)
//                .from(member)
//                .join(member.team, team)
//                .where(team.name.eq("A")) // inner join 이면 on절ㄹ 걸러내나 where 에서 거르나 똑같음, left join이라면 on으로 해야함..
//                .fetch();
//
        List<Tuple> result3 = query.select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .where(team.name.eq("A")) // inner join 이면 on절ㄹ 걸러내나 where 에서 거르나 똑같음, left join이라면 on으로 해야함..
                .fetch();
        for (Tuple tuple : result3) {
            System.out.println(tuple.toString());
        }
        List<Tuple> result4 = query.select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("A"))
                .fetch();
        for (Tuple tuple : result4) {
            System.out.println(tuple.toString());
        }
    }

    @Test
    public void 연관관계없는_테이블_leftJoin() {
        em.persist(new Member("A"));
        em.persist(new Member("B"));
        em.persist(new Member("C"));

        List<Tuple> members = query.select(member, team)
                .from(member)
                .leftJoin(team).on(member.name.eq(team.name)) // leftJoin( 인자로 1개만 넣으면 id로 조인을 하지 않고 on절에 나타난 조건으로 검색)
                .fetch();
        for (Tuple tuple : members) {
            System.out.println(tuple);
        }
    }

    @Test
    public void 연관관계없는_테이블_innerJoin() {
        em.persist(new Member("A"));
        em.persist(new Member("B"));
        em.persist(new Member("C"));

        List<Tuple> members = query.select(member, team)
                .from(member)
                .innerJoin(team).on(member.name.eq(team.name)) // leftJoin( 인자로 1개만 넣으면 id로 조인을 하지 않고 on절에 나타난 조건으로 검색)
                .fetch();
        for (Tuple tuple : members) {
            System.out.println(tuple);
        }
    }

    @Test
    public void noFetchJoin() {
        em.flush();
        em.clear();

        Member result = query.selectFrom(member)
                .where(member.name.eq("member1"))
                .fetchOne();
        System.out.println(result);
//        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(result.getTeam());
//        Assertions.assertThat(loaded).isFalse();
    }

    @Test
    public void FetchJoin() {
        em.flush();
        em.clear();

        Member result = query.select(member)
                .from(member)
                .innerJoin(member.team, team)
                .where(member.name.eq("member1"))
                .fetchOne();
        System.out.println(result.getTeam().getName());
    }

    @Test
    public void FetchJoin_dto_Projections() {
        em.flush();
        em.clear();

        // dto projections를 가져오게 되면 가져오는 select에서 team.name을 가져오기 때문에 lazy가 발생하지 않음
        MemberDto result = query.select(Projections.fields(MemberDto.class,
                team.name.as("name")))
                .from(member)
                .innerJoin(member.team, team)
                .where(member.name.eq("member1"))
                .fetchOne();
        System.out.println(result.getName());
    }

    @Test
    public void whereSubQuery() {
        QMember subMember = new QMember("subMember");

        List<Member> result = query.selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions.select(subMember.age.max())
                                .from(subMember)
                ))
                .fetch();
        Assertions.assertThat(result).extracting("age")
                .containsExactly(199);
    }

    @Test
    public void whereSubQuery2() {
        QMember subMember = new QMember("subMember");

        List<Member> result = query.selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions.select(subMember.age.avg())
                                .from(subMember)
                ))
                .fetch();
        Assertions.assertThat(result).extracting("age")
                .containsExactly(199);
    }

    @Test
    public void whereInSubQuery() {
        QMember subMember = new QMember("subMember");

        List<Member> result = query.selectFrom(member)
                .where(member.age.in(
                        JPAExpressions.select(subMember.age)
                                .from(subMember)
                                .where(subMember.age.gt(100))
                ))
                .fetch();
        Assertions.assertThat(result).extracting("age")
                .containsExactly(199);
    }

    @Test
    public void selectSubQuery() {
        QMember subMember = new QMember("subMember");

        List<Member> result = query.select(JPAExpressions.select(subMember)
                .from(subMember)
                .where(subMember.age.eq(member.age)))
                .from(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println(member1);
        }
        /*
            from 절에서는 서브쿼리가 안된다. 이건 querydsl의 한계임, 실제로 업무를 하면서 from 절에서
            서브쿼리를 사용하려고 헀으나 되지 않았음
            해결방안 : 서브쿼리를 join으로 바꿀 수 있음 (될 수도.. 안 될 수도..)
            or 네이티브 쿼리를 사용

            *) from 절에서 서브쿼리를 사용하는 이유는 많은데, 보통 안 좋은 이유가 많다
         */
    }

    @Test
    public void basciCase() {
        query.select(member.age
                .when(10).then("열살")
                .when(20).then("스무살")
                .otherwise("기타"))
                .from(member)
                .fetch();
    }

    @Test
    public void complexCase() {
        query.select(new CaseBuilder().when(member.age.between(0, 20)).then("0~20")
                .otherwise("나머지"))
                .from(member)
                .fetch();
        /*
            case when 은 db에서 안하는 것이 낫고, front 단에서 처리하는 것이 낫단
         */
    }
}
