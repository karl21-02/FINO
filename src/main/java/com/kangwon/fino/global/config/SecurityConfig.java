package com.kangwon.fino.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 설정 클래스임을 명시
@EnableWebSecurity // 웹 보안 기능 활성화
public class SecurityConfig {

    @Bean // Spring Bean으로 등록하여 스프링이 이 필터 체인을 관리하도록 함
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (API 서버에서 일반적으로 사용)
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI 및 OpenAPI 관련 URL 패턴을 인증 없이 허용 (permit)
                        // /**는 해당 경로 아래의 모든 하위 경로와 파일을 포함합니다.
                        .requestMatchers(
                                "/swagger-ui.html",         // Swagger UI 메인 페이지
                                "/swagger-ui/**",           // Swagger UI 정적 리소스 (JS, CSS 등)
                                "/v3/api-docs/**",          // OpenAPI 3 명세 (JSON/YAML)
                                "/swagger-resources/**",    // Swagger 리소스
                                "/webjars/**",              // 웹자 (프론트엔드 라이브러리)
                                "/api-docs/**"              // SpringDoc OpenAPI 기본 경로
                        ).permitAll() // 위 패턴에 해당하는 요청은 모두 허용
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated() // 나머지 모든 요청에 대해서는 인증된 사용자만 허용
                );
        // 만약 폼 로그인 페이지로 리다이렉션 되는 대신 403 Forbidden 에러가 바로 뜬다면
        // 기본 폼 로그인 설정을 명시적으로 비활성화해야 할 수 있습니다.
        // .formLogin(formLogin -> formLogin.disable()); // 기본 폼 로그인 비활성화 (필요시 주석 해제)
        // .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화 (필요시 주석 해제)

        return http.build(); // SecurityFilterChain 객체 반환
    }

    // 비밀번호 인코더 Bean (나중에 회원가입/로그인 구현 시 필요)
    // 지금 당장 로그인 오류와 직접 관련은 없지만, 나중에 필요할 수 있습니다.
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}