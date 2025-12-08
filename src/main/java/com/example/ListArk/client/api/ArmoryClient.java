package com.example.ListArk.client.api;

import com.example.ListArk.client.util.WebClientHelper;
import com.example.ListArk.dto.raw.armory.*;
import com.example.ListArk.dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.dto.raw.armory.card.ArmoryCardDto;
import com.example.ListArk.dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.dto.raw.armory.combatskill.CombatSkillDto;
import com.example.ListArk.dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.dto.raw.armory.profile.ArmoryProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmoryClient {

    private final WebClient webClient;

    public Mono<ArmoryDto> getCharacterArmory(String name){
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}",
                ArmoryDto.class,
                name
        );
    }

    public Mono<ArmoryProfileDto> getCharacterProfile(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/profiles",
                ArmoryProfileDto.class,
                name
        );
    }

    public Mono<EquipmentDto> getCharacterEquipment(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/equipments",
                EquipmentDto.class,
                name
        );
    }

    public Mono<ArmoryEngravingDto> getCharacterEngravings(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/engravings",
                ArmoryEngravingDto.class,
                name
        );
    }

    public Mono<ArmoryGemDto> getCharacterGems(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/gems",
                ArmoryGemDto.class,
                name
        );
    }

    public Mono<List<AvatarDto>> getCharacterAvatars(String name) {
        return WebClientHelper.getList(
                webClient,
                "/armories/characters/{name}/avatars",
                AvatarDto.class,
                name
        );
    }

    public Mono<List<CombatSkillDto>> getCharacterCombatSkills(String name) {
        return WebClientHelper.getList(
                webClient,
                "/armories/characters/{name}/combat-skills",
                CombatSkillDto.class,
                name
        );
    }

    public Mono<ArmoryCardDto> getCharacterCards(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/cards",
                ArmoryCardDto.class,
                name
        );
    }

    public Mono<ArmoryColosseumDto> getCharacterColosseums(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/colosseums",
                ArmoryColosseumDto.class,
                name
        );
    }

    public Mono<List<CollectibleDto>> getCharacterCollectibles(String name) {
        return WebClientHelper.getList(
                webClient,
                "/armories/characters/{name}/collectibles",
                CollectibleDto.class,
                name
        );
    }

    public Mono<ArmoryArkPassiveDto> getCharacterArkPassive(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/arkpassive",
                ArmoryArkPassiveDto.class,
                name
        );
    }

    public Mono<ArmoryArkGridDto> getCharacterArkGrid(String name) {
        return WebClientHelper.getMono(
                webClient,
                "/armories/characters/{name}/arkgrid",
                ArmoryArkGridDto.class,
                name
        );
    }
}
