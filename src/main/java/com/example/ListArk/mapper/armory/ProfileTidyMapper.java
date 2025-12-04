package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.profile.ArmoryProfileDto;
import com.example.ListArk.Dto.raw.armory.profile.StatDto;
import com.example.ListArk.Dto.raw.armory.profile.TendencyDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProfileTidyMapper {

    private static final Set<String> COMBAT_STATS =
            Set.of("치명", "특화", "신속", "제압", "인내", "숙련");

    public ProfileTidyDto toTidy(ArmoryDto raw) {
        ArmoryProfileDto p = NullSafe.get(raw::getArmoryProfile);
        if (p == null) {
            return new ProfileTidyDto();
        }

        ProfileTidyDto dto = new ProfileTidyDto();

        // 기본 정보
        dto.setCharacterName(NullSafe.get(p::getCharacterName, ""));
        dto.setCharacterClass(NullSafe.get(p::getCharacterClassName, ""));
        dto.setCharacterLevel(NullSafe.get(p::getCharacterLevel, 0));
        dto.setItemLevel(NullSafe.get(p::getItemAvgLevel, ""));
        dto.setServerName(NullSafe.get(p::getServerName, ""));
        dto.setGuildName(NullSafe.get(p::getGuildName, ""));
        dto.setTitle(NullSafe.get(p::getTitle, ""));
        dto.setPvpGrade(NullSafe.get(p::getPvpGradeName, ""));
        dto.setCharacterImage(NullSafe.get(p::getCharacterImage, ""));

        // 전투 특성 & 성향
        dto.setStats(
                extractCombatStats(NullSafe.get(p::getStats, List.of()))
        );
        dto.setTendencies(
                extractTendencyStats(NullSafe.get(p::getTendencies, List.of()))
        );

        return dto;
    }

    /**
     * Stats 배열에서 전투 특성만 추출
     */
    private Map<String, Integer> extractCombatStats(List<StatDto> stats) {
        Map<String, Integer> combatStats = new HashMap<>();

        for (StatDto stat : stats) {
            String type = stat.getType();
            if (type != null && COMBAT_STATS.contains(type)) {
                combatStats.put(type, safeInt(stat.getValue()));
            }
        }

        return combatStats;
    }

    /**
     * Tendencies 배열에서 성향 특성 추출
     */
    private Map<String, Integer> extractTendencyStats(List<TendencyDto> tendencies) {
        Map<String, Integer> tendencyMap = new HashMap<>();

        for (TendencyDto tendency : tendencies) {
            String type = tendency.getType();
            if (type != null) {
                tendencyMap.put(type, NullSafe.get(tendency::getPoint, 0));
            }
        }

        return tendencyMap;
    }

    private int safeInt(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}