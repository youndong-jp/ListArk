package com.example.ListArk.controller;

import com.example.ListArk.Dto.raw.notice.NoticeDto;
import com.example.ListArk.Dto.raw.notice.NoticeViewDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class ApiNoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notices")
    public ApiResponse<List<NoticeDto>> getNotices() {
        return ApiResponse.ok(noticeService.getNotices().block());
    }

    @GetMapping("/notices/filter")
    public ApiResponse<List<NoticeDto>> getFilteredNotices(@RequestParam(required = false) String type) {
        return ApiResponse.ok(noticeService.getFilteredNotices(type).block());
    }

    @GetMapping("/notices/views")
    public ApiResponse<List<NoticeViewDto>> getNoticeViews() {
        return ApiResponse.ok(noticeService.getNoticeViews().block());
    }
}


