package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridEffectDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridGemDto;
import com.example.ListArk.Dto.raw.armory.arkgrid.ArkGridSlotDto;
import com.example.ListArk.Dto.tidy.armory.arkgrid.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArkGridTidyMapper {

    public ArkGridTidyDto toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArkGrid() == null) {
            return null;
        }

        ArmoryArkGridDto source = raw.getArkGrid();

        ArkGridTidyDto dto = new ArkGridTidyDto();

        // Slots
        if (source.getSlots() != null) {
            dto.setSlots(
                    source.getSlots().stream()
                            .map(this::convertSlot)
                            .toList()
            );
        } else {
            dto.setSlots(List.of());
        }

        // Effects
        if (source.getEffects() != null) {
            dto.setEffects(
                    source.getEffects().stream()
                            .map(this::convertEffect)
                            .toList()
            );
        } else {
            dto.setEffects(List.of());
        }

        return dto;
    }

    private ArkGridTidyDto empty() {
        ArkGridTidyDto dto = new ArkGridTidyDto();
        dto.setSlots(List.of());
        dto.setEffects(List.of());
        return dto;
    }

    /** Slot 변환 */
    private ArkGridSlotTidyDto convertSlot(ArkGridSlotDto s) {

        ArkGridSlotTidyDto dto = new ArkGridSlotTidyDto();

        dto.setIndex(s.getIndex());
        dto.setIcon(s.getIcon());
        dto.setName(s.getName());
        dto.setPoint(s.getPoint());
        dto.setGrade(s.getGrade());
        dto.setTooltip(s.getTooltip());

        if (s.getGems() != null) {
            dto.setGems(
                    s.getGems().stream()
                            .map(this::convertGem)
                            .toList()
            );
        } else {
            dto.setGems(List.of());
        }

        return dto;
    }

    /** Slot 내부 Gem 변환 */
    private ArkGridGemTidyDto convertGem(ArkGridGemDto g) {

        ArkGridGemTidyDto dto = new ArkGridGemTidyDto();

        dto.setIndex(g.getIndex());
        dto.setIcon(g.getIcon());
        dto.setActive(g.isActive());
        dto.setGrade(g.getGrade());
        dto.setTooltip(g.getTooltip());

        return dto;
    }

    /** Effect 변환 */
    private ArkGridEffectTidyDto convertEffect(ArkGridEffectDto e) {

        ArkGridEffectTidyDto dto = new ArkGridEffectTidyDto();

        dto.setName(e.getName());
        dto.setLevel(e.getLevel());
        dto.setTooltip(e.getTooltip());

        return dto;
    }
}
