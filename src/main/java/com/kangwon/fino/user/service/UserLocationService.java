package com.kangwon.fino.user.service;

import com.kangwon.fino.user.dto.request.UserRequest;
import com.kangwon.fino.user.dto.response.UserResponse;

public interface UserLocationService {
    // 사용자 위치 업데이트
    boolean updateUserLocation(UserRequest.UserLocationUpdateDto requestDto);

    // 위치 기반 인증
    UserResponse.LocationAuthResultDto authenticateByLocation(UserRequest.LocationAuthDto requestDto);
}

