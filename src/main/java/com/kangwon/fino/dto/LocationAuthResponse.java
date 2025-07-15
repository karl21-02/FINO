package com.kangwon.fino.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationAuthResponse {
    private boolean isAuthenticated; // 위치 인증 성공 여부
    private String message;          // 인증 결과에 대한 메시지

}