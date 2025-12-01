package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.gem.GemDto;
import com.example.ListArk.Dto.raw.armory.gem.GemEffectDto;
import com.example.ListArk.Dto.raw.armory.gem.GemEffectSkillDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemSimpleDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GemTidyMapper {

    public GemTidyDto toTidy(ArmoryDto raw) {

        GemTidyDto dto = new GemTidyDto();

        if (raw == null || raw.getArmoryGem() == null) {
            dto.setGems(List.of());
            dto.setEffects(List.of());
            return dto;
        }

        ArmoryGemDto gemRaw = raw.getArmoryGem();

        // 1) 보석 리스트 tidy
        if (gemRaw.getGems() != null) {
            List<GemSimpleDto> gems = gemRaw.getGems().stream()
                    .map(this::convertGem)
                    .toList();

            dto.setGems(gems);
        } else {
            dto.setGems(List.of());
        }

        // 2) 보석 효과 tidy
        if (gemRaw.getEffects() != null && gemRaw.getEffects().getSkills() != null) {

            List<String> effects = gemRaw.getEffects().getSkills().stream()
                    .map(this::convertEffectSkill)
                    .toList();

            dto.setEffects(effects);

        } else {
            dto.setEffects(List.of());
        }

        return dto;
    }

    /** ✔ 개별 보석 변환 → GemSimpleDto */
    private GemSimpleDto convertGem(GemDto g) {

        GemSimpleDto dto = new GemSimpleDto();

        dto.setSlot(g.getSlot());
        dto.setName(g.getName());
        dto.setLevel(g.getLevel());
        dto.setGrade(g.getGrade());
        dto.setIcon(g.getIcon());

        return dto;
    }


    /** ✔ 보석 효과 변환 → 사람이 읽기 좋은 문자열 */
    private String convertEffectSkill(GemEffectSkillDto s) {

        String name = s.getName() != null ? s.getName() : "";

        String desc = (s.getDescription() != null && !s.getDescription().isEmpty())
                ? String.join(" ", s.getDescription())
                : "";

        return name + " - " + desc;
    }
}
