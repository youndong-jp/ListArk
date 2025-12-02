package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.profile.ArmoryProfileDto;
import com.example.ListArk.Dto.raw.armory.profile.StatDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProfileTidyMapper {

    // 상수로 분리 - 유지보수성 향상
    private static final Set<String> COMBAT_STATS =
            Set.of("치명", "특화", "신속", "제압", "인내", "숙련");

    private static final Set<String> TENDENCY_STATS =
            Set.of("지성", "담력", "매력", "친절");

    public ProfileTidyDto toTidy(ArmoryDto raw) {

        ArmoryProfileDto p = NullSafe.get(raw::getArmoryProfile);
        if (p == null) {
            return new ProfileTidyDto();
        }

        ProfileTidyDto dto = new ProfileTidyDto();

        dto.setCharacterName(NullSafe.get(p::getCharacterName, ""));
        dto.setCharacterClass(NullSafe.get(p::getCharacterClassName, ""));
        dto.setCharacterLevel(NullSafe.get(p::getCharacterLevel, 0));
        dto.setItemLevel(NullSafe.get(p::getItemAvgLevel, ""));
        dto.setServerName(NullSafe.get(p::getServerName, ""));
        dto.setGuildName(NullSafe.get(p::getGuildName, ""));
        dto.setTitle(NullSafe.get(p::getTitle, ""));
        dto.setPvpGrade(NullSafe.get(p::getPvpGradeName, ""));
        dto.setCharacterImage(NullSafe.get(p::getCharacterImage, ""));

        List<StatDto> stats = NullSafe.get(p::getStats, List.of());
        Map<String, Integer> combatStats = new HashMap<>();
        Map<String, Integer> tendencyStats = new HashMap<>();

        for (StatDto stat : stats) {
            String type = stat.getType();
            if (type == null) continue;

            int value = safeInt(stat.getValue());

            if (COMBAT_STATS.contains(type)) {
                combatStats.put(type, value);
            } else if (TENDENCY_STATS.contains(type)) {
                tendencyStats.put(type, value);
            }
        }

        dto.setStats(combatStats);
        dto.setTendencies(tendencyStats);

        return dto;
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