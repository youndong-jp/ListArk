package com.example.ListArk.service;

import com.example.ListArk.Dto.raw.armory.EngravingEffectDto;
import com.example.ListArk.Dto.raw.armory.StatDto;
import com.example.ListArk.Dto.raw.CharacterArmoryDto;
import com.example.ListArk.Dto.tidy.ProfileTidyDto;
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
    public ProfileTidyDto getCharacterProfile(String characterName) {

        CharacterArmoryDto raw = apiClientService
                .getCharacterProfile(characterName)
                .block();

        if (raw == null || raw.getArmoryProfile() == null) {
            throw new RuntimeException("캐릭터 정보를 찾을 수 없습니다: " + characterName);
        }

        return convertToTidy(raw);
    }

    private ProfileTidyDto convertToTidy(CharacterArmoryDto raw) {
        ProfileTidyDto dto = new ProfileTidyDto();

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
