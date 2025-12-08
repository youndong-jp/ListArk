package com.example.ListArk.mapper.armory;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.engraving.ArkPassiveEffectDto;
import com.example.ListArk.dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EngravingTidyMapper {

    public EngravingTidyDto toTidy(ArmoryDto raw) {
        ArmoryEngravingDto engravingRoot = NullSafe.get(raw::getArmoryEngraving);

        EngravingTidyDto dto = new EngravingTidyDto();

        if (engravingRoot == null) {
            dto.setEngravings(List.of());
            return dto;
        }

        List<ArkPassiveEffectDto> engravings = NullSafe.get(
                engravingRoot::getArkPassiveEffects,
                List.of()
        );

        // 각 각인을 "이름 Lv.레벨" 형식의 문자열로 변환
        List<String> engravingTexts = engravings.stream()
                .map(this::convertToText)
                .filter(text -> !text.isEmpty())  // 빈 문자열 제거
                .toList();

        dto.setEngravings(engravingTexts);
        return dto;
    }

    /** 각인 DTO를 "원한 Lv.3" 형식의 문자열로 변환 */
    private String convertToText(ArkPassiveEffectDto e) {
        String name = NullSafe.get(e::getName, "");
        String grade = NullSafe.get(e::getGrade,"");
        int level = NullSafe.get(e::getLevel, 0);

        // "원한 Lv.3" 형식으로 반환
        if (name.isEmpty()) return "";
        return  grade +" " + name + " Lv." + level;
    }
}