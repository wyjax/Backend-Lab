package com.wyjax.sociallogin.security.model;

import com.wyjax.sociallogin.domain.Member;
import com.wyjax.sociallogin.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attribute {
    private final Map<String, Object> attribute;
    private final String nameAttributeKey;
    private final String uid;        // 사용자 ID
    private final String name;       // 사용자 이름
    private final String serverName; // OAuth2 서버
    private final String picture;    // 프로필 사진

    static final String GOOGLE = "github";

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String uid,
                           String name,
                           String serverName,
                           String picture) {
        this.attribute = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.uid = uid;
        this.name = name;
        this.serverName = serverName;
        this.picture = picture;
    }

    // 정보 가져오기
    public static OAuth2Attribute of(String registrationId,
                                     String nameAttributeKey,
                                     Map<String, Object> attributes) {
        // Github, Facebook, Naver는 ofOOOO으로 따로 만들어 사용할 것

        // default Google
        return ofGoogle(registrationId, nameAttributeKey, attributes);
    }

    // Google 정보
    public static OAuth2Attribute ofGoogle(String registrationId,
                                           String nameAttributeKey,
                                           Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .name(attributes.get("name").toString() + "@" + registrationId)
                .serverName(registrationId)
                .build();
    }

    // OAuth2Attribute (DTO) to Member (Entity)
    public Member toEntity() {
        return Member.builder()
                .uid(uid)
                .name(name)
                .picture(picture)
                .serverName(serverName)
                .role(Role.USER)
                .build();
    }
}
