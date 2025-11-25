package com.example.ListArk.controller;

import com.example.ListArk.Dto.notice.NoticeDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.service.ApiClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiNoticeController {

    private final ApiClientService apiClientService;

    public ApiNoticeController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping("/notices")
    public ApiResponse<List<NoticeDto>> getNotices() {
        List<NoticeDto> notices = apiClientService.getNotices().block();
        return ApiResponse.ok(notices);
    }
    @GetMapping("/notices/filter")
    public ApiResponse<List<NoticeDto>> getFilteredNotices(
            @RequestParam(required = false) String type
    ) {
        List<NoticeDto> result = apiClientService.getFilteredNotices(type).block();
        return ApiResponse.ok(result);
    }

}
