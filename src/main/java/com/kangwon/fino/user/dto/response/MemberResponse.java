package com.kangwon.fino.user.dto.response;

import com.kangwon.fino.user.domain.RoleType;
import lombok.Builder;
import lombok.Getter;

public class MemberResponse {
    @Builder
    @Getter
    public static class SignInDto{
        private String email;
        private RoleType userAuth;
    }
}
