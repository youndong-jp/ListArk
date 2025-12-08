package com.example.ListArk.exception;

import org.springframework.http.HttpStatus;

/**
 * 모든 비즈니스 예외의 부모 클래스
 * - status: HTTP 상태 코드
 * - message: 에러 메시지
 */
public abstract class BusinessException extends RuntimeException {

    private final HttpStatus status;

    protected BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
