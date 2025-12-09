package com.example.ListArk.controller;

import com.example.ListArk.config.annotation.CommonErrorResponses;
import com.example.ListArk.dto.raw.notice.NoticeDto;
import com.example.ListArk.dto.raw.notice.NoticeViewDto;
import com.example.ListArk.dto.common.ApiResponse;
import com.example.ListArk.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@Tag(name = "공지사항 API", description = "로스트아크 공지사항/이벤트 조회 API")
public class NoticeController {

    private final NoticeService noticeService;

    // ----------------------------------------
    // 1) 전체 공지 조회
    // ----------------------------------------
    @Operation(
            summary = "전체 공지 목록 조회",
            description = "로스트아크 공식 API에서 공지사항 전체 목록을 조회합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "정상 조회",
            content = @Content(schema = @Schema(implementation = NoticeDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/notices")
    public ApiResponse<List<NoticeDto>> getNotices() {
        return ApiResponse.ok(noticeService.getNotices().block());
    }

    // ----------------------------------------
    // 2) 공지 유형 필터링 조회
    // ----------------------------------------
    @Operation(
            summary = "공지 유형 필터링 조회",
            description = "type 파라미터(예: '공지', '점검', '이벤트')에 따라 공지를 필터링합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "정상 조회",
            content = @Content(schema = @Schema(implementation = NoticeDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/notices/filter")
    public ApiResponse<List<NoticeDto>> getFilteredNotices(
            @RequestParam(required = false) String type
    ) {
        return ApiResponse.ok(noticeService.getFilteredNotices(type).block());
    }

    // ----------------------------------------
    // 3) 공지 상세 조회 목록
    // ----------------------------------------
    @Operation(
            summary = "공지 상세 조회 목록",
            description = "공지사항의 상세 뷰 데이터를 조회합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "정상 조회",
            content = @Content(schema = @Schema(implementation = NoticeViewDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/notices/views")
    public ApiResponse<List<NoticeViewDto>> getNoticeViews() {
        return ApiResponse.ok(noticeService.getNoticeViews().block());
    }
}
