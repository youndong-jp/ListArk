package com.example.ListArk.mapper.armory.arkgrid;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.dto.raw.armory.arkgrid.ArkGridEffectDto;
import com.example.ListArk.dto.raw.armory.arkgrid.ArkGridGemDto;
import com.example.ListArk.dto.raw.armory.arkgrid.ArkGridSlotDto;
import com.example.ListArk.dto.tidy.armory.arkgrid.*;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArkGridTidyMapper {

    private final ArkGridTooltipParser parser;

    public ArkGridTidyMapper(ArkGridTooltipParser parser) {
        this.parser = parser;
    }

    public ArkGridTidyDto toTidy(ArmoryDto raw) {
        ArmoryArkGridDto source = NullSafe.get(raw::getArkGrid);

        if (source == null) {
            return new ArkGridTidyDto();
        }

        ArkGridTidyDto dto = new ArkGridTidyDto();

        // Slots 변환
        List<ArkGridSlotTidyDto> slots = NullSafe.list(source.getSlots())
                .stream()
                .map(this::convertSlot)
                .toList();
        dto.setSlots(slots);

        // Effects 변환
        List<ArkGridEffectTidyDto> effects = NullSafe.list(source.getEffects())
                .stream()
                .map(this::convertEffect)
                .toList();
        dto.setEffects(effects);
        return dto;
    }

    /**
     * Slot 변환
     */
    private ArkGridSlotTidyDto convertSlot(ArkGridSlotDto s) {
        ArkGridSlotTidyDto dto = new ArkGridSlotTidyDto();


        // 기본 정보
        dto.setSlot(s.getIndex());
        dto.setIcon(NullSafe.get(s::getIcon, ""));
        dto.setName(NullSafe.get(s::getName, ""));
        dto.setPoint(NullSafe.get(s::getPoint, 0));
        dto.setGrade(NullSafe.get(s::getGrade, ""));


        // ✨ Slot tooltip 파싱
        String tooltip = NullSafe.get(s::getTooltip, "");
        parser.parseSlotTooltip(tooltip, dto);

        // Slot 내부 Gems 변환
        List<ArkGridGemTidyDto> gems = NullSafe.list(s.getGems())
                .stream()
                .map(this::convertGem)
                .toList();
        dto.setGems(gems);

        return dto;
    }

    /**
     * Gem 변환
     */
    private ArkGridGemTidyDto convertGem(ArkGridGemDto g) {
        ArkGridGemTidyDto dto = new ArkGridGemTidyDto();

        // 기본 정보
        dto.setSlot(g.getIndex());
        dto.setIcon(NullSafe.get(g::getIcon, ""));
        dto.setActive(g.isActive());
        dto.setGrade(NullSafe.get(g::getGrade, ""));

        // ✨ Gem tooltip 파싱
        String tooltip = NullSafe.get(g::getTooltip, "");
        parser.parseGemTooltip(tooltip, dto);

        return dto;
    }

    /**
     * Effect 변환
     */
    private ArkGridEffectTidyDto convertEffect(ArkGridEffectDto e) {
        ArkGridEffectTidyDto dto = new ArkGridEffectTidyDto();

        dto.setName(NullSafe.get(e::getName, ""));
        dto.setLevel(e.getLevel());

        // Effect는 tooltip 파싱 안 함 (name, level만으로 충분)

        return dto;
    }
}