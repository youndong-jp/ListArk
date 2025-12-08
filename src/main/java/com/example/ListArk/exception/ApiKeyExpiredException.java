package com.example.ListArk.exception;

import org.springframework.http.HttpStatus;

/**
 * API 키가 잘못되었거나 만료된 경우 발생
 * - HTTP 401 UNAUTHORIZED
 */
public class ApiKeyExpiredException extends BusinessException {

    // 기본 메시지
    public ApiKeyExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "API 키가 만료되었거나 유효하지 않습니다.");
    }

    // LostArk API에서 내려온 메시지를 사용할 수 있도록 지원
    public ApiKeyExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
