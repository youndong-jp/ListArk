package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArkPassiveEffectDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArkPassivePointDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveEffectTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassivePointTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArkPassiveTidyMapper {

    public ArkPassiveTidyDto toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArkPassive() == null) {
            return null;
        }

        ArmoryArkPassiveDto source = raw.getArkPassive();

        ArkPassiveTidyDto dto = new ArkPassiveTidyDto();

        dto.setActive(source.isArkPassive());

        // points
        if (source.getPoints() != null) {
            dto.setPoints(
                    source.getPoints().stream()
                            .map(this::convertPoint)
                            .toList()
            );
        } else {
            dto.setPoints(List.of());
        }

        // effects
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

    /** null 대응용 빈 객체 */
    private ArkPassiveTidyDto empty() {
        ArkPassiveTidyDto dto = new ArkPassiveTidyDto();
        dto.setActive(false);
        dto.setPoints(List.of());
        dto.setEffects(List.of());
        return dto;
    }

    private ArkPassivePointTidyDto convertPoint(ArkPassivePointDto p) {
        ArkPassivePointTidyDto dto = new ArkPassivePointTidyDto();
        dto.setName(p.getName());
        dto.setValue(p.getValue());
        return dto;
    }

    private ArkPassiveEffectTidyDto convertEffect(ArkPassiveEffectDto e) {
        ArkPassiveEffectTidyDto dto = new ArkPassiveEffectTidyDto();
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setIcon(e.getIcon());
        return dto;
    }
}
