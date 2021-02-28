package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;
import com.wyjax.datajpa.member.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Query("select m.username from Member m")
    List<String> findByUsernameList();

    // DTO 조회
    @Query("select new com.wyjax.datajpa.member.dto.MemberDto(m.username, m.age) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    // count 쿼리는 나중에 성능상 문제가 될 수 있다. 그래서 따로 쿼리로 countQuery로 해서 성능을 향상시킬 수 있다.
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge2(int age, Pageable pageable);

    // 엔티티는 애플리케이션에 노출시키면 안된다. 엔티티를 바꾸면 api 스펙이 바뀌기 때문이다. 그렇기 떄문에 dto로 변환해서 사용한다.
}
