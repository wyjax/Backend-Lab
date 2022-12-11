package com.wyjax.sociallogin.security.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    private static final long VALID_TIME = 30 * 60 * 1000L;

    @Value("")
    private final Key secretKey = Keys.hmacShaKeyFor("R$mcU{0,a-JckFf@tS_X's9y/U{EP9QU6V.H#@V/iV}ygP9)~csE-Ykr?!9ktTy".getBytes(StandardCharsets.UTF_8));

    public String createToken(Authentication authentication) {
        Date nowTime = new Date();
        List grant = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(nowTime)
                .claim("authorities", grant)
                .setExpiration(new Date(nowTime.getTime() + VALID_TIME))
                .signWith(secretKey)
                .compact();
    }

    private Jws<Claims> parseJws(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
