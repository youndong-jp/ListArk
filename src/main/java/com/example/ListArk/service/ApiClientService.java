package com.example.ListArk.service;

import java.util.List;

import com.example.ListArk.Dto.armory.ArmoryGemDto;
import com.example.ListArk.Dto.character.CharacterProfileDto;
import com.example.ListArk.Dto.SiblingCharacterDto;
import org.springframework.http.HttpHeaders;
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
    public Mono<List<SiblingCharacterDto>> getCharacterSiblings(String name) {
        return webClient.get()
                .uri("/characters/" + name + "/siblings")
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToFlux(SiblingCharacterDto.class)
                .collectList();
    }
    public Mono<CharacterProfileDto> getCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/" + name)
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(CharacterProfileDto.class);
    }
}
