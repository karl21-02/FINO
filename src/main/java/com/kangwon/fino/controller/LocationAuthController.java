package com.kangwon.fino.controller;

import com.kangwon.fino.dto.LocationAuthRequest;
import com.kangwon.fino.dto.LocationAuthResponse;
import com.kangwon.fino.service.LocationAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/location")
@RequiredArgsConstructor
public class LocationAuthController {

    private final LocationAuthService locationAuthService;

    /**
     * 사용자의 위치 인증을 요청하는 API 엔드포인트.
     * 클라이언트로부터 위치 인증 요청 DTO를 받아 서비스 로직을 수행하고 결과를 반환
     *
     * @param request 위치 인증 요청 DTO (JSON 형식의 요청 바디)
     * @return 위치 인증 결과 응답 DTO와 HTTP 상태 코드를 포함하는 ResponseEntity
     */
    @PostMapping
    public ResponseEntity<LocationAuthResponse> authenticateLocation(@RequestBody LocationAuthRequest request) {
        // 서비스 계층의 비즈니스 로직 호출
        LocationAuthResponse response = locationAuthService.authenticateByLocation(request);

        // 서비스 결과에 따라 적절한 HTTP 상태 코드와 함께 응답 반환
        if (response.isAuthenticated()) {
            return ResponseEntity.ok(response); // HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // HTTP 400 Bad Request
        }
    }

}