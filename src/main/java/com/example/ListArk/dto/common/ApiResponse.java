package com.example.ListArk.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "API 공통 응답 래퍼")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 데이터")
    private T data;

    @Schema(description = "응답 메시지", example = "정상 처리되었습니다", nullable = true)
    private String message;

    @Schema(description = "응답 시간", example = "2024-12-09T13:45:00")
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(
                true,
                data,
                null,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(
                true,
                data,
                message,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(
                false,
                null,
                message,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(
                false,
                data,
                message,
                LocalDateTime.now()
        );
    }
}