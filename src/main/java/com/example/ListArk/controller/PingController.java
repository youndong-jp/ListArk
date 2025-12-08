package com.example.ListArk.controller;

import com.example.ListArk.dto.ping.PingResponseDto;
import com.example.ListArk.service.PingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/ping")
    public PingResponseDto ping() {
        return pingService.getPingMessage();
    }
}
