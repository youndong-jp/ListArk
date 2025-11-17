package com.example.ListArk.controller;

import com.example.ListArk.service.ApiClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiCharacterController {

    private final ApiClientService apiClientService;

    public ApiCharacterController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping("/api/characters/{name}")
    public String getCharacter(@PathVariable String name) {

        System.out.println(">>> 캐릭터 조회 시작: " + name);

        String response = apiClientService.getCharacterInfo(name).block();

        System.out.println(">>> LostArk 캐릭터 응답: " + response);

        return response;
    }
}
