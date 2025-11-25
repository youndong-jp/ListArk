package com.example.ListArk.controller;

import com.example.ListArk.Dto.notice.NoticeDto;
import com.example.ListArk.Dto.notice.NoticeViewDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.service.ApiClientService;
import com.example.ListArk.service.NoticeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class NoticeController {

    private final ApiClientService apiClientService;
    private final NoticeService noticeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NoticeController(ApiClientService apiClientService, NoticeService noticeService) {
        this.apiClientService = apiClientService;
        this.noticeService = noticeService;
    }

    @GetMapping("/api/notices/view")
    public Mono<ApiResponse<List<NoticeViewDto>>> getNotices() {
        return apiClientService.getNotices()
                .map(notices -> {
                    List<NoticeViewDto> viewList = noticeService.convertToViewDto(notices);
                    return ApiResponse.ok(viewList);
                });
    }

}
