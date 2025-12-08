package com.example.ListArk.client.api;

import com.example.ListArk.Dto.raw.character.SiblingCharacterDto;
import com.example.ListArk.client.util.RetryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CharacterClient {

    private final WebClient webClient;

    public Mono<List<SiblingCharacterDto>> getCharacterSiblings(String name) {
        return webClient.get()
                .uri("/characters/{name}/siblings", name)
                .retrieve()
                .bodyToFlux(SiblingCharacterDto.class)
                .collectList()
                .transform(RetryUtils.retry3());
    }
}
