package com.example.ListArk.service;

import com.example.ListArk.Dto.ping.PingResponseDto;
import org.springframework.stereotype.Service;

@Service
public class PingService {

    public PingResponseDto getPingMessage() {
        return new PingResponseDto(
                "ListArk Server is running!",
                System.currentTimeMillis()
        );
    }
}
