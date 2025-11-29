package com.example.ListArk.service;

import java.util.List;

import com.example.ListArk.Dto.raw.CharacterArmoryDto;
import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.Dto.notice.NoticeDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.raw.armory.card.ArmoryCardDto;
import com.example.ListArk.Dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.raw.armory.combatskill.CombatSkillDto;
import com.example.ListArk.Dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.profile.ArmoryProfileDto;
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

    public Mono<CharacterArmoryDto> getCharacterArmory(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(CharacterArmoryDto.class);
    }


    public Mono<ArmoryProfileDto> getCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(ArmoryProfileDto.class);
    }

    public Mono<List<StatDto>> getCharacterStats(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/stats", name)
                .retrieve()
                .bodyToFlux(StatDto.class)
                .collectList();
    }

    public Mono<ArmoryEngravingDto> getCharacterEngravings(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(ArmoryEngravingDto.class);
    }

    public Mono<ArmoryGemDto> getCharacterGems(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(ArmoryGemDto.class);
    }

    public Mono<List<AvatarDto>> getCharacterAvatars(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToFlux(AvatarDto.class)
                .collectList();
    }
    public Mono<List<CombatSkillDto>> getCharacterCombatSkills(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/combat-skills", name)
                .retrieve()
                .bodyToFlux(CombatSkillDto.class)
                .collectList();
    }

    public Mono<ArmoryCardDto> getCharacterCards(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/cards", name)
                .retrieve()
                .bodyToMono(ArmoryCardDto.class);
    }

    public Mono<ArmoryColosseumDto> getCharacterColosseums(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/colosseums", name)
                .retrieve()
                .bodyToMono(ArmoryColosseumDto.class);
    }
    public Mono<List<CollectibleDto>> getCharacterCollectibles(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/collectibles", name)
                .retrieve()
                .bodyToFlux(CollectibleDto.class)
                .collectList();
    }

    public Mono<ArmoryArkPassiveDto> getCharacterArkPassive(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(ArmoryArkPassiveDto.class);
    }

    public Mono<ArmoryArkGridDto> getCharacterArkGrid(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkgrid", name)
                .retrieve()
                .bodyToMono(ArmoryArkGridDto.class);
    }

}
