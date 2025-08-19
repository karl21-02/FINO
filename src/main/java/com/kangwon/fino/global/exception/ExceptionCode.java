package com.kangwon.fino.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 4xxx 클라이언트 에러
    VALIDATION_FAILED(4000, "요청 유효성 검사에 실패하였습니다."),
    CONSTRAINT_VIOLATION(4001, "데이터 제약 조건을 위반하였습니다."),
    LOGIN_FAIL(4002, "아이디/비밀번호를 확인해주세요."),
    FORBIDDEN_ACCESS(4003, "접근 권한이 없습니다."),
    USER_NOT_FOUND(4004, "사용자를 찾을 수 없습니다."),
    REPORT_NOT_FOUND(4005, "보고서를 찾을 수 없습니다."),

    // 5xxx 서버 에러
    INTERNAL_SEVER_ERROR(5000, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
