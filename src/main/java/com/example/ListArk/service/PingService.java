package com.example.ListArk.service;

import org.springframework.stereotype.Service;

@Service
public class PingService {

    public String getPingMessage() {
        return "ListArk Server is running!";
    }
}
