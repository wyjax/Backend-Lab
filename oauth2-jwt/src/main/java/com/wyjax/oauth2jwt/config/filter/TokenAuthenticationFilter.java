package com.wyjax.oauth2jwt.config.filter;

import com.wyjax.oauth2jwt.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@RequiredArgsConstructor
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//    private final TokenProvider tokenProvider;
//    private final
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//    }
//}
