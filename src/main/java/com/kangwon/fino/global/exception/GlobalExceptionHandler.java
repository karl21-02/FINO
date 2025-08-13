package com.kangwon.fino.global.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    // @Valid 검증 실패 시 (MethodArgumentNotValidException) 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // 유효성 검사 실패 메시지를 하나의 문자열로 합쳐서 BadRequestException에 전달
        String fullMessage = "유효성 검사 실패: " + errors.toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(4000, fullMessage));
    }

    // @NotNull, @Size 등 직접적인 제약 조건 위반 (ConstraintViolationException) 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // 유효성 검사 실패 메시지를 ErrorResponse 형태로 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(4001, "제약 조건 위반: " + ex.getMessage()));
    }

    // EntityNotFoundException 처리 (예: ID로 사용자를 찾을 수 없을 때)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        // EntityNotFoundException의 메시지를 ErrorResponse 형태로 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                .body(new ErrorResponse(4004, ex.getMessage()));
    }

    // 그 외 모든 예외에 대한 일반적인 처리 (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        ex.printStackTrace(); // 서버 로그에 스택 트레이스 출력
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ExceptionCode.INTERNAL_SEVER_ERROR.getCode(), ExceptionCode.INTERNAL_SEVER_ERROR.getMessage()));
    }
}
