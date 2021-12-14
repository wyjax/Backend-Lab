package com.wyjax.querydsl.member.repository;

import com.wyjax.querydsl.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
