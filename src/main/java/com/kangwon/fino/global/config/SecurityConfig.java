package com.kangwon.fino.global.config;

import com.kangwon.fino.jwt.JWTFilter;
import com.kangwon.fino.jwt.JWTUtil;
import com.kangwon.fino.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.PasswordManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// private final만 사용 ㄱㄴ
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

//    private final TokenProvider tokenProvider;
    // jwt를 발급하고 검증하는 기능을 담당
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // 인증되지 않은 사용자가 요청할 경우 호출되는 예외 처리
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    // 로그인은 했지만 권한이 없는 사용자가 요청할 때 예외 처리

    // 비밀번호 복호화 방식 선택
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token 사용 방식 -- csrf : disable
                .csrf(csrf -> csrf.disable())

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )




                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI 및 OpenAPI 관련 URL 패턴을 인증 없이 허용 (permit)
                        .requestMatchers(
                                "/swagger-ui.html",         // Swagger UI 메인 페이지
                                "/swagger-ui/**",           // Swagger UI 정적 리소스 (JS, CSS 등)
                                "/v3/api-docs/**",          // OpenAPI 3 명세 (JSON/YAML)
                                "/swagger-resources/**",    // Swagger 리소스
                                "/webjars/**",              // 웹자 (프론트엔드 라이브러리)
                                "/api-docs/**",              // SpringDoc OpenAPI 기본 경로
                                "/api/auteht"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/sign/in",          // 로그인 API 허용 (필요)
                                "/api/v1/users/**",         // 사용자 위치 설정 API 허용 (임시)
                                "/api/v1/auth/location"     // 위치 인증 API 허용 (임시)
                        ).permitAll()
                        .requestMatchers("/login", "/", "/join").permitAll()

                        .anyRequest().authenticated() // 나머지 모든 요청에 대해서는 인증된 사용자만 허용
                );
        httpSecurity
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


        httpSecurity
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정
        httpSecurity
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Form 로그인 방식 disable
        httpSecurity
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        httpSecurity
                .httpBasic((auth) -> auth.disable());


        // 만약 폼 로그인 페이지로 리다이렉션 되는 대신 403 Forbidden 에러가 바로 뜬다면
        // 기본 폼 로그인 설정을 명시적으로 비활성화해야 할 수 있음
        // .formLogin(formLogin -> formLogin.disable()); // 기본 폼 로그인 비활성화
        // .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화

        return httpSecurity.build(); // SecurityFilterChain 객체 반환
    }
}