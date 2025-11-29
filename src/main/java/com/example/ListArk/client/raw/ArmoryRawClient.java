package com.example.ListArk.client.raw;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class ArmoryRawClient {

    private final WebClient webClient;

    public Mono<String> getRawCharacterArmory(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(String.class);
    }


    public Mono<String> getRawCharacterGems(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterStats(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/stats", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterEngravings(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterAvatars(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterCombatSkills(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/combat-skills", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterCards(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/cards", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterColosseums(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/colosseums", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterCollectibles(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/collectibles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterArkPassive(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getRawCharacterArkGrid(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkgrid", name)
                .retrieve()
                .bodyToMono(String.class);
    }
}
