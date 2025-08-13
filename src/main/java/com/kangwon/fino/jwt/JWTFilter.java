package com.kangwon.fino.jwt;

import com.kangwon.fino.domain.TblUser;
import com.kangwon.fino.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 에서 헤더(Authorization) 찾기
        String authorization= request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            // 헤더가 null 이거나 특정 토큰으로 시작되지 않는다면 종료
            return;
        }

        String token = authorization.split(" ")[1];

        // 토큰의 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);


            return;
        }

        String username =  jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        TblUser tblUser = new TblUser();
        tblUser.setUsername(username);
        tblUser.setRole(role);
        // 비밀번호는 토큰에 담겨있지 않음 --> 임시 비밀번호 사용
        tblUser.setPassword("temppassword");

        CustomUserDetails customUserDetails = new CustomUserDetails(tblUser);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 세션에 사용자 등록하기
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
