package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager em;

    public List<Member> findAllMembers() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
