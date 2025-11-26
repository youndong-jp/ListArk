package com.example.ListArk.service;

import com.example.ListArk.Dto.armory.EngravingEffectDto;
import com.example.ListArk.Dto.armory.StatDto;
import com.example.ListArk.Dto.character.CharacterProfileDto;
import com.example.ListArk.Dto.character.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final ApiClientService apiClientService;

    @Override
    public ProfileDto getCharacterProfile(String characterName) {

        CharacterProfileDto raw = apiClientService
                .getCharacterProfile(characterName)
                .block();

        if (raw == null || raw.getArmoryProfile() == null) {
            throw new RuntimeException("캐릭터 정보를 찾을 수 없습니다: " + characterName);
        }

        return convertToTidy(raw);
    }

    private ProfileDto convertToTidy(CharacterProfileDto raw) {
        ProfileDto dto = new ProfileDto();

        var p = raw.getArmoryProfile();

        dto.setCharacterName(p.getCharacterName());
        dto.setServerName(p.getServerName());
        dto.setCharacterClassName(p.getCharacterClassName());
        dto.setCharacterLevel(p.getCharacterLevel());
        dto.setItemLevel(p.getItemAvgLevel());
        dto.setGuildName(p.getGuildName());
        dto.setTitle(p.getTitle());
        dto.setPvpGradeName(p.getPvpGradeName());

        // ⭐
        if (raw.getArmoryStat() != null) {
            dto.setStats(
                    raw.getArmoryStat().stream()
                            .collect(Collectors.toMap(
                                    StatDto::getType,
                                    s -> Integer.parseInt(s.getValue())
                            ))
            );
        } else {
            dto.setStats(Map.of());
        }

        //
        if (raw.getArmoryEngraving() != null &&
                raw.getArmoryEngraving().getEngravings() != null) {

            dto.setEngravings(
                    raw.getArmoryEngraving().getEngravings().stream()
                            .map(EngravingEffectDto::getName)
                            .toList()
            );
        } else {
            dto.setEngravings(List.of());
        }

        return dto;
    }
}
