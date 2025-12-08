package com.example.ListArk.client.raw;

import com.example.ListArk.client.util.WebClientHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArmoryRawClient {

    private final WebClient webClient;

    public Mono<String> getRawCharacterArmory(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}",
                name
        );
    }

    public Mono<String> getRawCharacterProfile(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/profiles",
                name
        );
    }

    public Mono<String> getRawCharacterEquipment(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/equipment",
                name
        );
    }

    public Mono<String> getRawCharacterGems(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/gems",
                name
        );
    }

    public Mono<String> getRawCharacterEngravings(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/engravings",
                name
        );
    }

    public Mono<String> getRawCharacterAvatars(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/avatars",
                name
        );
    }

    public Mono<String> getRawCharacterCombatSkills(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/combat-skills",
                name
        );
    }

    public Mono<String> getRawCharacterCards(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/cards",
                name
        );
    }

    public Mono<String> getRawCharacterColosseums(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/colosseums",
                name
        );
    }

    public Mono<String> getRawCharacterCollectibles(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/collectibles",
                name
        );
    }

    public Mono<String> getRawCharacterArkPassive(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/arkpassive",
                name
        );
    }

    public Mono<String> getRawCharacterArkGrid(String name) {
        return WebClientHelper.getRaw(
                webClient,
                "/armories/characters/{name}/arkgrid",
                name
        );
    }
}
