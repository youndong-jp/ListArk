package com.example.ListArk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PingResponseDto {
    private String message;
    private long timestamp;
}
