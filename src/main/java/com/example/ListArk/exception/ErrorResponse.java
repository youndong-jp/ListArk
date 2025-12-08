package com.example.ListArk.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 공통 에러 응답 DTO

 * 응답 예시:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "해당 캐릭터를 찾을 수 없습니다.",
 *   "path": "/characters/abc",
 *   "timestamp": "2025-12-07T15:23:12"
 * }
 */
@Getter
@Builder
public class ErrorResponse {

    /**
     * HTTP 상태 코드
     * 예: 400, 401, 404, 500 …
     * 프론트엔드가 에러 유형을 분기할 때 사용함.
     */
    private int status;

    /**
     * HTTP 에러 이름
     * 예: "Bad Request", "Unauthorized", "Not Found", "Internal Server Error"
     * 개발자/로그 분석자가 한눈에 에러를 파악 가능.
     */
    private String error;

    /**
     * 상세 에러 메시지
     * 예: "캐릭터를 찾을 수 없습니다"
     * 사용자가 이해할 수 있는 형태 또는 내부 로직 설명 포함.
     */
    private String message;

    /**
     * 에러가 발생한 요청 경로
     * 예: "/characters/{name}"
     * 문제 발생 지점을 추적하는 데 매우 유용함.
     */
    private String path;

    /**
     * 에러가 발생한 시간 (서버 기준)
     * - 운영 환경에서 문제 발생 시점 확인 가능
     * - 로그 타임라인 분석에 필수
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
