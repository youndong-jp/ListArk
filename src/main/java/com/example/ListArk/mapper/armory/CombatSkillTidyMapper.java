package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.combatskill.CombatSkillDto;
import com.example.ListArk.Dto.raw.armory.combatskill.TripodDto;
import com.example.ListArk.Dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.Dto.tidy.armory.combatskill.TripodTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CombatSkillTidyMapper {

    public List<CombatSkillTidyDto> toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArmorySkills() == null) {
            return List.of();
        }

        return raw.getArmorySkills().stream()
                .map(this::convert)
                .toList();
    }

    /** CombatSkillDto → CombatSkillTidyDto */
    private CombatSkillTidyDto convert(CombatSkillDto s) {

        CombatSkillTidyDto dto = new CombatSkillTidyDto();

        dto.setName(s.getName());
        dto.setLevel(s.getLevel());
        dto.setIcon(s.getIcon());

        // Tripods tidy
        if (s.getTripods() != null) {
            List<TripodTidyDto> tripodList = s.getTripods().stream()
                    .map(this::convertTripod)
                    .toList();
            dto.setTripods(tripodList);
        } else {
            dto.setTripods(List.of());
        }

        // 룬은 “이름 Lv.?” 형태로 표시
        dto.setRune(s.getRune() != null ? s.getRune().getName() : null);

        return dto;
    }

    /** TripodDto → TripodTidyDto */
    private TripodTidyDto convertTripod(TripodDto t) {

        TripodTidyDto dto = new TripodTidyDto();

        dto.setTier(t.getTier());
        dto.setSlot(t.getSlot());
        dto.setName(t.getName());
        dto.setSelected(t.isSelected());

        return dto;
    }
}
