package com.kangwon.fino.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 4xxx 로그인
    LOGIN_FAIL(4002, "아이디/비밀번호를 확인해주세요."),

    // 5xxx 서버에러
    INTERNAL_SEVER_ERROR(5000, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
