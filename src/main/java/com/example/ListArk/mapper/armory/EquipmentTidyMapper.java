package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipmentTidyMapper {

    public List<EquipmentTidyDto> toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArmoryEquipment() == null) {
            return List.of();
        }

        return raw.getArmoryEquipment().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /** 개별 장비 변환 메소드 */
    private EquipmentTidyDto convert(EquipmentDto e) {

        EquipmentTidyDto dto = new EquipmentTidyDto();

        dto.setGrade(e.getType());
        dto.setName(e.getName());
        dto.setIcon(e.getIcon());
        dto.setGrade(e.getGrade());

        // 품질 (Quality)
        dto.setQuality(String.valueOf(e.getGrade()));

        // 아이템 레벨(ItemLevel)
        dto.setItemLevel(e.getGrade());

        // Tooltip 그대로
        dto.setTooltip(e.getTooltip());

        return dto;
    }
}
