package com.example.ListArk.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiClientService {

    private final WebClient webClient;
    private final String apiKey = System.getenv("LOSTARK_API_KEY");

    public ApiClientService(WebClient webClient) {
        this.webClient = webClient;
        System.out.println("Loaded API KEY = " + apiKey);
    }


    public Mono<String> getNotices() {
        return webClient.get()
                .uri("/news/notices")
                .header("accept", "application/json")
                .header("authorization", "bearer " + apiKey)  // ← 수정됨!
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> getCharacterInfo(String name) {
        return webClient.get()
                .uri("/characters/" + name + "/siblings")
                .header("accept", "application/json")
                .header("authorization", "bearer " + apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }


    }
