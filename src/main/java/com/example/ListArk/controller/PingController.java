package com.example.ListArk.controller;

import com.example.ListArk.config.annotation.CommonErrorResponses;
import com.example.ListArk.dto.ping.PingResponseDto;
import com.example.ListArk.service.PingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ping API", description = "서버 상태 점검용 Ping API")
public class PingController {

    private final PingService pingService;

    @Operation(
            summary = "Ping 테스트",
            description = "서버가 정상적으로 동작하는지 확인하기 위한 간단한 Ping API입니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "서버 응답 성공",
            content = @Content(schema = @Schema(implementation = PingResponseDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/ping")
    public PingResponseDto ping() {
        return pingService.getPingMessage();
    }
}
