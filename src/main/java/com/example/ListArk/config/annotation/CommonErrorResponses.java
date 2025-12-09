package com.example.ListArk.config.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.ListArk.exception.ErrorResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * API 공통 에러 응답 스키마
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(
                responseCode = "404",
                description = "캐릭터를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "401",
                description = "API Key 인증 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "429",
                description = "요청 제한 (Too Many Requests)",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "500",
                description = "서버 내부 오류",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
})
public @interface CommonErrorResponses {
}