package com.example.ListArk.service;

import java.util.List;

import com.example.ListArk.Dto.raw.CharacterArmoryDto;
import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.Dto.notice.NoticeDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.profile.StatDto;
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

    public Mono<String> getRawCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}", name)
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

    public Mono<CharacterArmoryDto> getCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(CharacterArmoryDto.class);
    }
    // Stats (특성)
    public Mono<List<StatDto>> getCharacterStats(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/stats", name)
                .retrieve()
                .bodyToFlux(StatDto.class)
                .collectList();
    }

    // Engravings (각인)
    public Mono<ArmoryEngravingDto> getCharacterEngravings(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(ArmoryEngravingDto.class);
    }

    // Gems (보석)
    public Mono<ArmoryGemDto> getCharacterGems(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(ArmoryGemDto.class);
    }

    // Avatars (아바타)
    public Mono<List<AvatarDto>> getCharacterAvatars(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToFlux(AvatarDto.class)
                .collectList();
    }

}
