package com.example.ListArk.mapper.armory;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.combatskill.CombatSkillDto;
import com.example.ListArk.dto.raw.armory.combatskill.RuneDto;
import com.example.ListArk.dto.raw.armory.combatskill.TripodDto;
import com.example.ListArk.dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.dto.tidy.armory.combatskill.TripodTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CombatSkillTidyMapper {

    public List<CombatSkillTidyDto> toTidy(ArmoryDto raw) {
        List<CombatSkillDto> skills = NullSafe.get(raw::getArmorySkills, List.<CombatSkillDto>of());

        return skills.stream()
                .map(this::convertSkill)
                .toList();
    }

    private CombatSkillTidyDto convertSkill(CombatSkillDto s) {
        CombatSkillTidyDto dto = new CombatSkillTidyDto();

        dto.setName(NullSafe.get(s::getName, ""));
        dto.setIcon(NullSafe.get(s::getIcon, ""));
        dto.setLevel(NullSafe.get(s::getLevel, 0));

        // 트라이포드 변환
        List<TripodDto> tripods = NullSafe.get(s::getTripods, List.<TripodDto>of());
        dto.setTripods(
                tripods.stream()
                        .map(this::convertTripod)
                        .toList()
        );

        // 룬 이름 추출 (RuneDto → String)
        dto.setRune(extractRuneName(s.getRune()));

        return dto;
    }

    private TripodTidyDto convertTripod(TripodDto t) {
        TripodTidyDto dto = new TripodTidyDto();

        dto.setTier(NullSafe.get(t::getTier, 0));
        dto.setSlot(NullSafe.get(t::getSlot, 0));
        dto.setName(NullSafe.get(t::getName, ""));
        dto.setSelected(t.isSelected());  // boolean이므로 기본값 불필요

        return dto;
    }

    /** RuneDto에서 룬 이름만 추출 */
    private String extractRuneName(RuneDto rune) {
        if (rune == null) return "";
        return NullSafe.get(rune::getName, "");
    }
}