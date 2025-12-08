package com.example.ListArk.exception;

import org.springframework.http.HttpStatus;

/**
 * 외부 API 호출 실패 시 사용되는 예외
 * - LostArk API 오류 (5xx 등)
 */
public class ExternalApiException extends BusinessException {

    public ExternalApiException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "외부 API 오류: " + message);
    }
}
