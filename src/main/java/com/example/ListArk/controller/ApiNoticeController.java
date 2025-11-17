package com.example.ListArk.controller;

import com.example.ListArk.service.ApiClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiNoticeController {

    private final ApiClientService apiClientService;

    public ApiNoticeController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping("/api/notices")
    public String getNotices() {
        System.out.println(">>> LostArk 공지사항 호출 시작");

        String response = apiClientService.getNotices().block();

        System.out.println(">>> LostArk API 응답: " + response);

        return response;
    }
}
