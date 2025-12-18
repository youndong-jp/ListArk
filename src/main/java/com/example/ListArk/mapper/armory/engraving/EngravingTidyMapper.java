package com.example.ListArk.mapper.armory.engraving;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.engraving.ArkPassiveEffectDto;
import com.example.ListArk.dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingDetailDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EngravingTidyMapper {

    public EngravingTidyDto toTidy(ArmoryDto raw) {

        EngravingTidyDto tidy = new EngravingTidyDto();

        if (raw == null) {
            tidy.setEngravings(List.of());
            return tidy;
        }

        ArmoryEngravingDto engravingRoot = NullSafe.get(raw::getArmoryEngraving);
        if (engravingRoot == null) {
            tidy.setEngravings(List.of());
            return tidy;
        }

        List<ArkPassiveEffectDto> effects =
                NullSafe.get(engravingRoot::getArkPassiveEffects, List.of());

        List<EngravingDetailDto> engravingList = effects.stream()
                .map(this::mapEffect)
                .toList();

        tidy.setEngravings(engravingList);
        return tidy;
    }

    /** RAW → Tidy 구조화 변환 */
    private EngravingDetailDto mapEffect(ArkPassiveEffectDto e) {

        EngravingDetailDto dto = new EngravingDetailDto();

        dto.setName(NullSafe.get(e::getName, ""));
        dto.setGrade(NullSafe.get(e::getGrade, ""));
        String desc = NullSafe.get(e::getDescription, "");
        dto.setDescription(EngravingTooltipParser.cleanDescription(desc));
        int baseLevel = NullSafe.get(e::getLevel, 0);
        Integer stoneLevel = NullSafe.get(e::getAbilityStoneLevel);
        dto.setStoneLevel(stoneLevel);

        // 최종 레벨 = 각인 레벨 vs 어빌리티 스톤 레벨 중 더 높은 값
        int finalLevel = Math.max(baseLevel, stoneLevel != null ? stoneLevel : 0);
        dto.setLevel(finalLevel);

        return dto;
    }
}
