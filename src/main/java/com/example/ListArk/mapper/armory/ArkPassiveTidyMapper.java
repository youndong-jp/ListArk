package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArkPassiveEffectDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArkPassivePointDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveEffectTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassivePointTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArkPassiveTidyMapper {

    public ArkPassiveTidyDto toTidy(ArmoryDto raw) {
        ArmoryArkPassiveDto source = NullSafe.get(raw::getArkPassive, null);

        if (source == null) {
            return new ArkPassiveTidyDto();
        }

        ArkPassiveTidyDto dto = new ArkPassiveTidyDto();

        dto.setActive(NullSafe.get(source::isArkPassive, false));

        // Points 변환
        List<ArkPassivePointTidyDto> points =
                NullSafe.list(source.getPoints())
                        .stream()
                        .map(this::convertPoint)
                        .toList();
        dto.setPoints(points);

        // Effects 변환
        List<ArkPassiveEffectTidyDto> effects =
                NullSafe.list(source.getEffects())
                        .stream()
                        .map(this::convertEffect)
                        .toList();
        dto.setEffects(effects);

        return dto;
    }

    /**
     * Raw Point → Tidy Point
     */
    private ArkPassivePointTidyDto convertPoint(ArkPassivePointDto p) {
        ArkPassivePointTidyDto dto = new ArkPassivePointTidyDto();

        dto.setName(NullSafe.get(p::getName, ""));
        dto.setValue(NullSafe.get(p::getValue, 0));

        return dto;
    }

    /**
     * Raw Effect → Tidy Effect
     */
    private ArkPassiveEffectTidyDto convertEffect(ArkPassiveEffectDto e) {
        ArkPassiveEffectTidyDto dto = new ArkPassiveEffectTidyDto();

        dto.setName(NullSafe.get(e::getName, ""));
        dto.setDescription(NullSafe.get(e::getDescription, ""));
        dto.setIcon(NullSafe.get(e::getIcon, ""));

        return dto;
    }
}