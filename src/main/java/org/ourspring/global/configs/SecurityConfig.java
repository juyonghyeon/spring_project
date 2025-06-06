package org.ourspring.global.configs;

import lombok.RequiredArgsConstructor;
import org.ourspring.member.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberInfoService memberInfoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 - 로그인, 로그아웃 S */
        http.formLogin(c -> {
            c.loginPage("/member/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 - 로그인, 로그아웃 E */

        /* 자동로그인 - RememberMe */
        http.rememberMe(c -> {
            c.rememberMeParameter("autoLogin")
                    .tokenValiditySeconds(60*60*24*7)
                    .userDetailsService(memberInfoService)
                    .authenticationSuccessHandler(new LoginSuccessHandler()); // 7일간 자동 로그인 유지
        });

        /* 인가 설정 - 자원에 대한 접근 권한 설정 S */
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated()
                    .requestMatchers("/member/join","/member/join").anonymous()
                    //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .anyRequest().permitAll();
        });

        http.exceptionHandling(c-> {
            c.authenticationEntryPoint(new MemberAuthenticationExceptionHandler()); // 미로그인 상태에서의 인가 실패에 대한 처리
            c.accessDeniedHandler(new MemberAccessDeniedHandler());
        });
        /* 인가 설정 - 자원에 대한 접근 권한 설정 E */

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
