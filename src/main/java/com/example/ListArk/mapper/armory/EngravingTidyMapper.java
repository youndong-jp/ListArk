package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.engraving.EngravingDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EngravingTidyMapper {

    public EngravingTidyDto toTidy(ArmoryDto raw) {

        EngravingTidyDto dto = new EngravingTidyDto();

        // ArmoryDto에서 각인 목록 가져오기
        if (raw == null
                || raw.getArmoryEngraving() == null
                || raw.getArmoryEngraving().getEngravings() == null) {

            dto.setEngravings(List.of());
            return dto;
        }

        // 각인 리스트 → ["원한 Lv.3", "예리한 둔기 Lv.3"] 변환
        List<String> tidyList = raw.getArmoryEngraving().getEngravings().stream()
                .map(this::convert)
                .toList();

        dto.setEngravings(tidyList);
        return dto;
    }

    /** EngravingDto -> "원한 Lv.3" */
    private String convert(EngravingDto e) {

        String name = e.getName();
        int level = extractLevel(e);

        return String.format("%s Lv.%d", name, level);
    }


    /** Tooltip에서 레벨 추출 (Lv.3, Lv.2, Lv.1) */
    private int extractLevel(EngravingDto e) {

        if (e.getTooltip() == null) return 0;

        String t = e.getTooltip();

        if (t.contains("Lv.3")) return 3;
        if (t.contains("Lv.2")) return 2;
        if (t.contains("Lv.1")) return 1;

        return 0;
    }
}
