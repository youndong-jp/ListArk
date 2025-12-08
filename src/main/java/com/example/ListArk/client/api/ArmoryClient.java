package com.example.ListArk.client.api;

import java.util.List;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.raw.armory.card.ArmoryCardDto;
import com.example.ListArk.Dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.raw.armory.combatskill.CombatSkillDto;
import com.example.ListArk.Dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.Dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.profile.ArmoryProfileDto;
import com.example.ListArk.Dto.raw.armory.profile.StatDto;

import com.example.ListArk.client.util.RetryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArmoryClient {

    private final WebClient webClient;

    public Mono<ArmoryDto> getCharacterArmory(String name){
        return webClient.get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(ArmoryDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryProfileDto> getCharacterProfile(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(ArmoryProfileDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<EquipmentDto> getCharacterEquipment(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/equipments", name)
                .retrieve()
                .bodyToMono(EquipmentDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryEngravingDto> getCharacterEngravings(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(ArmoryEngravingDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryGemDto> getCharacterGems(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(ArmoryGemDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<List<AvatarDto>> getCharacterAvatars(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToFlux(AvatarDto.class)
                .collectList()
                .transform(RetryUtils.retry3());
    }

    public Mono<List<CombatSkillDto>> getCharacterCombatSkills(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/combat-skills", name)
                .retrieve()
                .bodyToFlux(CombatSkillDto.class)
                .collectList()
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryCardDto> getCharacterCards(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/cards", name)
                .retrieve()
                .bodyToMono(ArmoryCardDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryColosseumDto> getCharacterColosseums(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/colosseums", name)
                .retrieve()
                .bodyToMono(ArmoryColosseumDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<List<CollectibleDto>> getCharacterCollectibles(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/collectibles", name)
                .retrieve()
                .bodyToFlux(CollectibleDto.class)
                .collectList()
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryArkPassiveDto> getCharacterArkPassive(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(ArmoryArkPassiveDto.class)
                .transform(RetryUtils.retry3());
    }

    public Mono<ArmoryArkGridDto> getCharacterArkGrid(String name) {
        return webClient.get()
                .uri("/armories/characters/{name}/arkgrid", name)
                .retrieve()
                .bodyToMono(ArmoryArkGridDto.class)
                .transform(RetryUtils.retry3());
    }
}