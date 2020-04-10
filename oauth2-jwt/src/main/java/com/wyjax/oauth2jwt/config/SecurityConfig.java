package com.wyjax.oauth2jwt.config;

import com.wyjax.oauth2jwt.config.filter.HttpCookieOAuth2AuthorizationRequestRepository;
import com.wyjax.oauth2jwt.config.filter.OAuth2AuthenticationSuccessHandler;
import com.wyjax.oauth2jwt.config.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2Service;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/h2-console/**")
                .ignoringAntMatchers("/push")
                .and()
                .headers().frameOptions().sameOrigin();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll();
//                .anyRequest()
//                .authenticated();
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2Service)
                .and()
                .authorizationEndpoint()
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
