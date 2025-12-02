package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvatarTidyMapper {

    /**
     * 통합 ArmoryDto → List<AvatarTidyDto>
     */
    public List<AvatarTidyDto> toTidy(ArmoryDto raw) {
        List<AvatarDto> avatars =
                NullSafe.list(NullSafe.get(raw::getArmoryAvatar, null));

        return avatars.stream()
                .map(this::convert)
                .toList();
    }

    /**
     * 개별 AvatarDto → AvatarTidyDto 변환
     */
    private AvatarTidyDto convert(AvatarDto a) {
        AvatarTidyDto dto = new AvatarTidyDto();

        dto.setType(NullSafe.get(a::getType, ""));
        dto.setName(NullSafe.get(a::getName, ""));
        dto.setIcon(NullSafe.get(a::getIcon, ""));
        dto.setGrade(NullSafe.get(a::getGrade, ""));

        dto.setSet(NullSafe.get(a::isSet, false));
        dto.setInner(NullSafe.get(a::isInner, false));

        dto.setTooltip(NullSafe.get(a::getTooltip, ""));

        return dto;
    }
}