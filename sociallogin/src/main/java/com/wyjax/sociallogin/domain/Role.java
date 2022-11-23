package com.wyjax.sociallogin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "손님"),
    ADMIN("ROLE_ADMIN", "운영자");

    private final String key;
    private final String title;
}