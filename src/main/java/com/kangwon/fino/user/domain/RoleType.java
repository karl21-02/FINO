package com.kangwon.fino.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN", "시스템 관리자 권한"),
    NONE("ROLE_NONE", "알 수 없는 권한");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(NONE);
    }
}
