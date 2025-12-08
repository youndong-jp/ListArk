package com.example.ListArk.mapper.armory;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.dto.raw.armory.gem.GemDto;
import com.example.ListArk.dto.raw.armory.gem.GemEffectDto;
import com.example.ListArk.dto.raw.armory.gem.GemEffectSkillDto;
import com.example.ListArk.dto.tidy.armory.gem.GemEffectTidyDto;
import com.example.ListArk.dto.tidy.armory.gem.GemSimpleDto;
import com.example.ListArk.dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class GemTidyMapper {

    public GemTidyDto toTidy(ArmoryDto raw) {

        GemTidyDto dto = new GemTidyDto();

        ArmoryGemDto gemRoot = NullSafe.get(raw::getArmoryGem);
        if (gemRoot == null) {
            dto.setGems(List.of());
            dto.setEffects(List.of());
            return dto;
        }

        // Gems 변환 (List → List)
        List<GemDto> gems = NullSafe.get(gemRoot::getGems, List.of());
        List<GemSimpleDto> gemSimpleList = gems.stream()
                .map(this::convertToSimple)
                .toList();
        dto.setGems(gemSimpleList);

        // Effects 변환 (단일 객체 → List)
        GemEffectDto effects = NullSafe.get(gemRoot::getEffects);
        List<GemEffectTidyDto> gemEffectList = effects != null
                ? convertEffectsToTidy(effects)
                : List.of();
        dto.setEffects(gemEffectList);

        return dto;
    }


    private GemEffectTidyDto convertSkillToEffect(GemEffectSkillDto skill) {
        GemEffectTidyDto dto = new GemEffectTidyDto();

        dto.setGemSlot(skill.getGemSlot());
        dto.setName(NullSafe.get(skill::getName, ""));

        // List<String> → String 변환
        List<String> descriptions = NullSafe.get(skill::getDescription, List.of());
        dto.setDescription(String.join(" / ", descriptions));

        dto.setIcon(NullSafe.get(skill::getIcon, ""));

        return dto;
    }

    private List<GemEffectTidyDto> convertEffectsToTidy(GemEffectDto effects) {
        List<GemEffectSkillDto> skills = NullSafe.get(effects::getSkills, List.of());

        return skills.stream()
                .map(this::convertSkillToEffect)
                .toList();
    }
    /** Gem → 기본 정보 DTO 변환 */
    private GemSimpleDto convertToSimple(GemDto g) {

        GemSimpleDto dto = new GemSimpleDto();

        dto.setSlot(NullSafe.get(g::getSlot, 0));
        dto.setType(extractGemType(NullSafe.get(g::getName, "")));
        dto.setLevel(NullSafe.get(g::getLevel, 0));
        dto.setGrade(NullSafe.get(g::getGrade, ""));
        dto.setIcon(NullSafe.get(g::getIcon, ""));


        return dto;
    }

    /** "멸화의 보석" → "멸화" */
    private String extractGemType(String name) {
        if (name == null || name.isBlank()) {
            return "";
        }

        // "9레벨 멸화의 보석" → "멸화"
        int startIdx = name.indexOf("레벨 ");
        int endIdx = name.indexOf("의 보석");

        if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
            return name.substring(startIdx + 3, endIdx).trim();
        }

        return name;
    }
}