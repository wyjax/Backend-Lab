package com.wyjax.datajpa.member.repository;

import com.wyjax.datajpa.member.domain.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

// Repository 메서드를 구현해서 사용하고 싶을 경우 이렇게 사용
// class 이름은 MemberRepository interface랑 맞춰주고 뒤에 Impl로 지정 > 관례인듯?
// 웬만하면 관례를 따르는 것이 좋다. 유지보수를 하는데 다른 사람들도 생각하자..ㅋㅋ
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final EntityManager em;


    @Override
    public List<Member> findMemberCustom() {
        // 복잡한 쿼리를 작성해야 한다면 이렇게 작성한다.
        // querydsl을 사용할 때 이렇게 custom해서 많이 사용한다. 간단한 것은 jpa가 제공하는 것을 사용하자

        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
