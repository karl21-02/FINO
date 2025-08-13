/*
package com.kangwon.fino.user.controller;


import com.kangwon.fino.global.exception.ErrorResponse;
import com.kangwon.fino.user.dto.request.MemberRequest;
import com.kangwon.fino.user.dto.response.MemberResponse;
import com.kangwon.fino.user.service.LoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign")
public class UserController {

    private final LoginServiceImpl loginService;

    /**
     * 로그인 API
     * @param dto MemberRequest.LoginDto
     * @return ResponseEntity
     */
/*
    @PostMapping("/in")
    @Operation(summary = "로그인 API", description = "사용자가 아이디와 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResponse.SignInDto.class))),
            @ApiResponse(responseCode = "4002", description = "아이디/비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> signIn(@RequestBody MemberRequest.SignInDto dto) {
        return ResponseEntity.ok().body(loginService.signIn(dto));
    }

}
*/
