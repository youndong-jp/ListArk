package com.example.ListArk.service.tidy;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.tidy.armory.ArmoryTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkgrid.ArkGridTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.Dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.Dto.tidy.armory.collectible.CollectibleTidyDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.Dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;

import com.example.ListArk.client.api.ArmoryClient;
import com.example.ListArk.mapper.armory.*;

import com.example.ListArk.mapper.armory.arkgrid.ArkGridTidyMapper;
import com.example.ListArk.mapper.armory.avatar.AvatarTidyMapper;
import com.example.ListArk.mapper.armory.equipment.EquipmentTidyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArmoryTidyService {

    private final ArmoryClient armoryClient;

    private final ProfileTidyMapper profileMapper;
    private final EquipmentTidyMapper equipmentMapper;
    private final AvatarTidyMapper avatarMapper;
    private final EngravingTidyMapper engravingMapper;
    private final GemTidyMapper gemMapper;
    private final CombatSkillTidyMapper combatSkillMapper;
    private final CardTidyMapper cardMapper;
    private final CollectiblesTidyMapper collectiblesMapper;
    private final ColosseumTidyMapper colosseumMapper;
    private final ArkPassiveTidyMapper arkPassiveMapper;
    private final ArkGridTidyMapper arkGridMapper;

    // 공통 Raw 조회 메소드

    private ArmoryDto getRaw(String name) {

        ArmoryDto raw = armoryClient
                .getCharacterArmory(name)
                .block();

        if (raw == null) {
            log.warn("{}의 RAW Armory = NULL", name);
        }

        return raw;
    }


    // 공통 로깅 + null 처리 메소드

    private <T> T logCheck(String name, T value) {

        if (value == null) {
            log.warn("[TIDY] {} → NULL", name);
            return null;
        }

        if (value instanceof List<?> list) {
            if (list.isEmpty()) {
                log.debug("[TIDY] {} → empty list", name);
            }
            return value;
        }

        return value;
    }

    //  통합 Armory tidy 변환

    public ArmoryTidyDto toTidy(String characterName) {
        ArmoryDto raw = getRaw(characterName);
        return toTidy(raw);
    }

    //  raw → tidy

    public ArmoryTidyDto toTidy(ArmoryDto raw) {

        if (raw == null) {
            log.warn("[TIDY] Raw ArmoryDto NULL → empty tidy return");
            return empty();
        }

        log.info("[TIDY] Start converting ArmoryDto → Tidy");

        ArmoryTidyDto dto = new ArmoryTidyDto();

        dto.setProfile(logCheck("Profile", profileMapper.toTidy(raw)));
        dto.setEquipment(logCheck("Equipment", equipmentMapper.toTidy(raw)));
        dto.setAvatars(logCheck("Avatars", avatarMapper.toTidy(raw)));
        dto.setEngravings(logCheck("Engravings", engravingMapper.toTidy(raw)));
        dto.setGems(logCheck("Gems", gemMapper.toTidy(raw)));
        dto.setCombatSkills(logCheck("CombatSkills", combatSkillMapper.toTidy(raw)));
        dto.setCards(logCheck("Cards", cardMapper.toTidy(raw)));
        dto.setCollectibles(logCheck("Collectibles", collectiblesMapper.toTidy(raw)));
        dto.setColosseum(logCheck("Colosseum", colosseumMapper.toTidy(raw)));
        dto.setArkPassive(logCheck("ArkPassive", arkPassiveMapper.toTidy(raw)));
        dto.setArkGrid(logCheck("ArkGrid", arkGridMapper.toTidy(raw)));

        log.info("[TIDY] Conversion complete.");
        return dto;
    }

    //  Controller에서 사용하는 개별 조회
    public ProfileTidyDto getProfile(String name) {
        return profileMapper.toTidy(getRaw(name));
    }

    public List<EquipmentTidyDto> getEquipment(String name) {
        return equipmentMapper.toTidy(getRaw(name));
    }

    public GemTidyDto getGems(String name) {
        return gemMapper.toTidy(getRaw(name));
    }

    public EngravingTidyDto getEngravings(String name) {
        return engravingMapper.toTidy(getRaw(name));
    }

    public List<AvatarTidyDto> getAvatars(String name) {
        return avatarMapper.toTidy(getRaw(name));
    }

    public List<CombatSkillTidyDto> getCombatSkills(String name) {
        return combatSkillMapper.toTidy(getRaw(name));
    }

    public CardTidyDto getCards(String name) {
        return cardMapper.toTidy(getRaw(name));
    }

    public List<CollectibleTidyDto> getCollectibles(String name) {
        return collectiblesMapper.toTidy(getRaw(name));
    }

    public ColosseumTidyDto getColosseum(String name) {
        return colosseumMapper.toTidy(getRaw(name));
    }

    public ArkPassiveTidyDto getArkPassive(String name) {
        return arkPassiveMapper.toTidy(getRaw(name));
    }

    public ArkGridTidyDto getArkGrid(String name) {
        return arkGridMapper.toTidy(getRaw(name));
    }


    // 비어 있는 tidy 객체 제공

    private ArmoryTidyDto empty() {
        ArmoryTidyDto dto = new ArmoryTidyDto();

        dto.setProfile(new ProfileTidyDto());
        dto.setEquipment(List.of());
        dto.setAvatars(List.of());
        dto.setEngravings(new EngravingTidyDto());
        dto.setGems(new GemTidyDto());
        dto.setCombatSkills(List.of());
        dto.setCards(new CardTidyDto());
        dto.setCollectibles(List.of());
        dto.setColosseum(new ColosseumTidyDto());
        dto.setArkPassive(new ArkPassiveTidyDto());
        dto.setArkGrid(new ArkGridTidyDto());

        return dto;
    }
}
