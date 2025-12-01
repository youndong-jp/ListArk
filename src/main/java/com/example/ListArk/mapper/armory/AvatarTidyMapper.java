package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvatarTidyMapper {

    /** 통합 ArmoryDto → List<AvatarTidyDto> */
    public List<AvatarTidyDto> toTidy(ArmoryDto raw) {

        if (raw == null || raw.getArmoryAvatar() == null) {
            return List.of();
        }

        return raw.getArmoryAvatar().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /** 개별 AvatarDto → AvatarTidyDto 변환 */
    private AvatarTidyDto convert(AvatarDto a) {

        AvatarTidyDto dto = new AvatarTidyDto();

        dto.setType(a.getType());
        dto.setName(a.getName());
        dto.setIcon(a.getIcon());
        dto.setGrade(a.getGrade());

        dto.setTooltip(a.getTooltip());

        return dto;
    }
}
