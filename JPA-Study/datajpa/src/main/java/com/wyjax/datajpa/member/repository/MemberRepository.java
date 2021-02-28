package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 인터페이스로 되어있는데 실행시점에 구현체를 스프링이 만들어줘서 인스턴스에 넣어준다.
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    // Named Query > 네임드 쿼리는 로딩 시점에 버그를 잡을 수 있다.
    // 이렇게 돌리면 JPQL 이 Member.findByUsername 를 찾는다.
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 이름을 간략하게 사용할 수 있다. 복잡한 것을 해결 가능
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    /*
        쿼리가 복잡할 수 있는 것은 네임드쿼리보다는 @query안에 쿼리를 짜는 것이 좋다.
        - 동적쿼리
            하는 방법은 QueryDsl을 사용하는 것이 좋다. 다른 방법도 여러가지가 있긴하다. 하지만
            querydsl이 유지보수 하기에도 좋은 것 같다.
     */
}
