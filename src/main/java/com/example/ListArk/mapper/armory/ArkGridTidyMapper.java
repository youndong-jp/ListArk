package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridEffectDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridGemDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridSlotDto;
import com.example.ListArk.Dto.tidy.armory.arkgrid.*;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArkGridTidyMapper {

    public ArkGridTidyDto toTidy(ArmoryDto raw) {
        ArmoryArkGridDto source = NullSafe.get(raw::getArkGrid, null);

        if (source == null) {
            return empty();
        }

        ArkGridTidyDto dto = new ArkGridTidyDto();

        // Slots 변환
        List<ArkGridSlotTidyDto> slots =
                NullSafe.list(source.getSlots())
                        .stream()
                        .map(this::convertSlot)
                        .toList();
        dto.setSlots(slots);

        // Effects 변환
        List<ArkGridEffectTidyDto> effects =
                NullSafe.list(source.getEffects())
                        .stream()
                        .map(this::convertEffect)
                        .toList();
        dto.setEffects(effects);

        return dto;
    }

    /**
     * Null 대응용 빈 객체
     */
    private ArkGridTidyDto empty() {
        ArkGridTidyDto dto = new ArkGridTidyDto();
        dto.setSlots(List.of());
        dto.setEffects(List.of());
        return dto;
    }

    /**
     * Slot 변환
     */
    private ArkGridSlotTidyDto convertSlot(ArkGridSlotDto s) {
        ArkGridSlotTidyDto dto = new ArkGridSlotTidyDto();

        dto.setSlot(NullSafe.get(s::getIndex, 0));
        dto.setIcon(NullSafe.get(s::getIcon, ""));
        dto.setName(NullSafe.get(s::getName, ""));
        dto.setPoint(NullSafe.get(s::getPoint, 0));
        dto.setGrade(NullSafe.get(s::getGrade, ""));
        dto.setTooltip(NullSafe.get(s::getTooltip, ""));

        // Slot 내부 Gems 변환
        List<ArkGridGemTidyDto> gems =
                NullSafe.list(s.getGems())
                        .stream()
                        .map(this::convertGem)
                        .toList();
        dto.setGems(gems);

        return dto;
    }

    /**
     * Slot 내부 Gem 변환
     */
    private ArkGridGemTidyDto convertGem(ArkGridGemDto g) {
        ArkGridGemTidyDto dto = new ArkGridGemTidyDto();

        dto.setSlot(NullSafe.get(g::getIndex, 0));
        dto.setIcon(NullSafe.get(g::getIcon, ""));
        dto.setActive(NullSafe.get(g::isActive, false));
        dto.setGrade(NullSafe.get(g::getGrade, ""));
        dto.setTooltip(NullSafe.get(g::getTooltip, ""));

        return dto;
    }

    /**
     * Effect 변환
     */
    private ArkGridEffectTidyDto convertEffect(ArkGridEffectDto e) {
        ArkGridEffectTidyDto dto = new ArkGridEffectTidyDto();

        dto.setName(NullSafe.get(e::getName, ""));
        dto.setLevel(NullSafe.get(e::getLevel, 0));
        dto.setTooltip(NullSafe.get(e::getTooltip, ""));

        return dto;
    }
}