package com.wyjax.oauth2jwt.config.filter;

import com.wyjax.oauth2jwt.config.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    private final String HEADER_NAME = "Authorization";
    private final String TOKEN_NAME_START = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String JwtToken = getJwtFromRequest(request);

            if (StringUtils.hasText(JwtToken) && tokenProvider.validateToken(JwtToken)) {
                String uid = tokenProvider.getInfoFromToken(JwtToken);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(uid,
                                                                                                   null,
                                                                                                   null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_NAME);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_NAME_START)) {
            return bearerToken.substring(TOKEN_NAME_START.length(), bearerToken.length());
        }
        return null;
    }
}
