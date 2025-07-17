package com.kangwon.fino.user.controller;

import com.kangwon.fino.global.exception.ErrorResponse;
import com.kangwon.fino.user.dto.request.UserRequest;
import com.kangwon.fino.user.dto.response.UserResponse;
import com.kangwon.fino.user.service.UserLocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationAuthController {

    private final UserLocationService userLocationService;

    @PutMapping("/users/{userId}/location")
    @Operation(summary = "사용자 기준 위치 설정 API", description = "사용자의 위도, 경도 기준 위치를 설정하거나 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위치 정보 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "요청 유효성 검사 실패 또는 ID 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자 ID를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> updateUserLocation(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest.UserLocationUpdateDto requestDto
    ) {
        if (!userId.equals(requestDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("경로의 사용자 ID와 요청 바디의 사용자 ID가 일치하지 않습니다.");
        }

        userLocationService.updateUserLocation(requestDto);

        return ResponseEntity.ok("사용자 ID " + userId + "의 위치 정보가 성공적으로 업데이트되었습니다.");
    }

    @PostMapping("/auth/location")
    @Operation(summary = "위치 인증 API", description = "사용자의 현재 위치를 기준으로 인증 여부를 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위치 인증 성공 또는 실패 (결과 메시지 확인)",
                    content = @Content(schema = @Schema(implementation = UserResponse.LocationAuthResultDto.class))),
            @ApiResponse(responseCode = "400", description = "요청 유효성 검사 실패 또는 인증 실패 (허용 범위 벗어남 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자 ID를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserResponse.LocationAuthResultDto> authenticateLocation(
            @Valid @RequestBody UserRequest.LocationAuthDto requestDto
    ) {
        UserResponse.LocationAuthResultDto response = userLocationService.authenticateByLocation(requestDto); // 변경된 서비스 호출

        if (response.isAuthenticated()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}