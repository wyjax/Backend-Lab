package com.wyjax.oauth2jwt.config.security;

import com.wyjax.oauth2jwt.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TokenProvider {

    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    // JWT Token create
    public String createToken(Authentication authentication) {
        Date nowTime = new Date();
        List grant = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(nowTime)
                .claim("authorities", grant)
                .setExpiration(new Date(nowTime.getTime() + appProperties.getAuth().getTokenExpirationMsec()))
                .signWith(SignatureAlgorithm.HS256, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public String getInfoFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
