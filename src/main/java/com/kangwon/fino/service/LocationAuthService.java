package com.kangwon.fino.service;

import com.kangwon.fino.dto.LocationAuthRequest;
import com.kangwon.fino.dto.LocationAuthResponse;

public interface LocationAuthService {
    /**
     * 사용자의 현재 위치를 기반으로 인증을 수행합니다.
     * 사용자의 등록된 기준 위치와 클라이언트가 전송한 현재 위치 간의 거리를 계산하여
     * 허용된 오차 범위 내에 있는지 확인합니다.
     *
     * @param request 위치 인증 요청 DTO (사용자 ID, 현재 위도/경도 포함)
     * @return 위치 인증 결과 응답 DTO (인증 성공 여부, 메시지 포함)
     */
    LocationAuthResponse authenticateByLocation(LocationAuthRequest request);
}