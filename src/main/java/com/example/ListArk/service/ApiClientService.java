package com.example.ListArk.service;

import java.util.List;

import com.example.ListArk.Dto.raw.CharacterArmoryDto;
import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.Dto.notice.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApiClientService {

    private final WebClient webClient;

    public Mono<List<NoticeDto>> getNotices() {
        return webClient.get()
                .uri("/news/notices")
                .retrieve()
                .bodyToFlux(NoticeDto.class)
                .collectList();
    }

    public Mono<List<NoticeDto>> getFilteredNotices(String type) {
        return getNotices()
                .map(list -> list.stream()
                        .filter(n -> type == null || n.getType().equals(type))
                        .sorted((a, b) -> b.getDate().compareTo(a.getDate())) // 최신순 정렬
                        .toList());
    }

    public Mono<List<SiblingCharacterDto>> getCharacterSiblings(String name) {
        return webClient.get()
                .uri("/characters/{name}/siblings", name)
                .retrieve()
                .bodyToFlux(SiblingCharacterDto.class)
                .collectList();
    }

    public Mono<CharacterArmoryDto> getCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(CharacterArmoryDto.class);
    }
}
