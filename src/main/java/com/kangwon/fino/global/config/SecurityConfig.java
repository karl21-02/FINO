package com.kangwon.fino.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (API 서버에서 일반적으로 사용)
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI 및 OpenAPI 관련 URL 패턴을 인증 없이 허용 (permit)
                        .requestMatchers(
                                "/swagger-ui.html",         // Swagger UI 메인 페이지
                                "/swagger-ui/**",           // Swagger UI 정적 리소스 (JS, CSS 등)
                                "/v3/api-docs/**",          // OpenAPI 3 명세 (JSON/YAML)
                                "/swagger-resources/**",    // Swagger 리소스
                                "/webjars/**",              // 웹자 (프론트엔드 라이브러리)
                                "/api-docs/**"              // SpringDoc OpenAPI 기본 경로
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/sign/in",          // 로그인 API 허용 (필요)
                                "/api/v1/users/**",         // 사용자 위치 설정 API 허용 (임시)
                                "/api/v1/auth/location"     // 위치 인증 API 허용 (임시)
                        ).permitAll()
                        .anyRequest().authenticated() // 나머지 모든 요청에 대해서는 인증된 사용자만 허용
                );
        // 만약 폼 로그인 페이지로 리다이렉션 되는 대신 403 Forbidden 에러가 바로 뜬다면
        // 기본 폼 로그인 설정을 명시적으로 비활성화해야 할 수 있음
        // .formLogin(formLogin -> formLogin.disable()); // 기본 폼 로그인 비활성화
        // .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화

        return http.build(); // SecurityFilterChain 객체 반환
    }
}