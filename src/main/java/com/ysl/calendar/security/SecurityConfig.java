package com.ysl.calendar.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final TokenProvider jwtTokenProvider;

    private final String[] allowedUrls = {"/user/check-email", "/user/add", "/user/login", "/user/check-nickname"};

    /* 패스워드 암호화 모듈 */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    /* 필터 체인 생략 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // @todo 화면 페이지 접근 허용할때 활용하기
        return (web) -> web.ignoring().requestMatchers("/user/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http.csrf(CsrfConfigurer<HttpSecurity>::disable)
                   .authorizeHttpRequests(requests ->
//                           requests.requestMatchers( allowedUrls).permitAll() // 허용 url 리스트
                           requests.requestMatchers("/**").permitAll() // 테스트를 위해 전체 허용
                                   .anyRequest().authenticated()
                   )
                   .sessionManagement(sessionManagement ->
                           sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   )
                   .exceptionHandling(exceptionHandling -> exceptionHandling
                           .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                   .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                   .build();
    }
}
