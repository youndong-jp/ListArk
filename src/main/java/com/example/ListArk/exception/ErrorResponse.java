package com.example.ListArk.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 공통 에러 응답 DTO
 *
 * 응답 예시:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "해당 캐릭터를 찾을 수 없습니다.",
 *   "path": "/characters/abc",
 *   "timestamp": "2025-12-07T15:23:12"
 * }
 */
@Schema(description = "에러 응답 객체")
@Getter
@Builder
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드", example = "404")
    private int status;

    @Schema(description = "HTTP 에러 이름", example = "Not Found")
    private String error;

    @Schema(description = "상세 에러 메시지", example = "캐릭터를 찾을 수 없습니다")
    private String message;

    @Schema(description = "에러가 발생한 요청 경로", example = "/api/characters/홍길동")
    private String path;

    @Schema(description = "에러 발생 시간", example = "2025-12-09T15:23:12")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}