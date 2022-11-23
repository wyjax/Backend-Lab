package com.wyjax.sociallogin.security.service;


import com.wyjax.sociallogin.domain.Member;
import com.wyjax.sociallogin.domain.MemberRepository;
import com.wyjax.sociallogin.security.model.OAuth2Attribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService userService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = userService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 사용자 정보 가져옴
        OAuth2Attribute attribute = OAuth2Attribute.of(registrationId,
                userName,
                oAuth2User.getAttributes());
        Member member = saveOrUpdate(attribute);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
                attribute.getAttribute(),
                attribute.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuth2Attribute attribute) {
        Member member = memberRepository.findByUid(attribute.getUid())
                .map(m -> m.update(attribute.getPicture()))
                .orElse(attribute.toEntity());
        return memberRepository.save(member);
    }
}
