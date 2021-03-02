package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
