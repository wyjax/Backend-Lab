package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스로 되어있는데 실행시점에 구현체를 스프링이 만들어줘서 인스턴스에 넣어준다.
public interface MemberRepository extends JpaRepository<Member, Long> {

}
