package com.kangwon.fino.controller;

import io.swagger.v3.oas.annotations.Operation; // import
import io.swagger.v3.oas.annotations.tags.Tag; // import
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "사용자 API", description = "사용자 등록, 조회 등 관련 기능 제공") // API 그룹 설명
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Operation(summary = "헬로 월드", description = "간단한 헬로 월드 메시지를 반환합니다.") // API 요약 및 설명
    @GetMapping("/hello")
    public String hello() {
        return "Hello from User API!";
    }

    // 예시: 사용자 등록 API
    // @Operation(summary = "새 사용자 등록", description = "새로운 사용자 계정을 등록합니다.")
    // @PostMapping("/register")
    // public ResponseEntity<User> registerUser(@RequestBody User user) {
    //     // ... 서비스 호출 로직 ...
    //     return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    // }
}