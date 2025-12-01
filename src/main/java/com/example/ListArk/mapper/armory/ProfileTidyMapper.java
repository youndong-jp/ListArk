package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.profile.StatDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProfileTidyMapper {

    public ProfileTidyDto toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArmoryProfile() == null) {
            return null;
        }

        var p = raw.getArmoryProfile();

        ProfileTidyDto dto = new ProfileTidyDto();

        dto.setCharacterName(p.getCharacterName());
        dto.setCharacterClass(p.getCharacterClassName());
        dto.setCharacterLevel(p.getCharacterLevel());
        dto.setItemLevel(p.getItemAvgLevel());
        dto.setServerName(p.getServerName());
        dto.setGuildName(p.getGuildName());
        dto.setTitle(p.getTitle());
        dto.setPvpGrade(p.getPvpGradeName());
        dto.setCharacterImage(p.getCharacterImage());

        if (p.getStats() != null) {
            Map<String, Integer> stats = p.getStats().stream()
                    .filter(s -> s.getType() != null && isCombatStat(s.getType()))
                    .collect(Collectors.toMap(
                            StatDto::getType,
                            s -> safeInt(s.getValue())
                    ));
            dto.setStats(stats);
        }

        if (p.getStats() != null) {
            Map<String, Integer> tendencies = p.getStats().stream()
                    .filter(s -> s.getType() != null && isTendency(s.getType()))
                    .collect(Collectors.toMap(
                            StatDto::getType,
                            s -> safeInt(s.getValue())
                    ));
            dto.setTendencies(tendencies);
        }

        return dto;
    }

    /***
     * 전투 특성 판단용
     */
    private boolean isCombatStat(String type) {
        return switch (type) {
            case "치명", "특화", "신속", "제압", "인내", "숙련" -> true;
            default -> false;
        };
    }

    /***
     * 성향 특성 판단용
     */
    private boolean isTendency(String type) {
        return switch (type) {
            case "지성", "담력", "매력", "친절" -> true;
            default -> false;
        };
    }

    /***
     * "1234" → 1234 변환 + null 대응
     */
    private int safeInt(String value) {
        try {
            return Integer.parseInt(value.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}
