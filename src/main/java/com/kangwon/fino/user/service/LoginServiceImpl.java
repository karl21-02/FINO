package com.kangwon.fino.user.service;

import com.kangwon.fino.user.domain.RoleType;
import com.kangwon.fino.user.dto.request.MemberRequest;
import com.kangwon.fino.user.dto.response.MemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl {

    public MemberResponse.SignInDto signIn(MemberRequest.SignInDto dto) {
        // 5. 조회 결과 전달
        return MemberResponse.SignInDto.builder()
                .email("manuna530@gmail.com")
                .userAuth(RoleType.ADMIN)
                .build();
    }
}
