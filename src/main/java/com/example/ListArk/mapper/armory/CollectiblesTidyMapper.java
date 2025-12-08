package com.example.ListArk.mapper.armory;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.dto.raw.armory.collectibles.CollectiblePointDto;
import com.example.ListArk.dto.tidy.armory.collectible.CollectiblePointTidyDto;
import com.example.ListArk.dto.tidy.armory.collectible.CollectibleTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectiblesTidyMapper {

    public List<CollectibleTidyDto> toTidy(ArmoryDto raw) {
        List<CollectibleDto> collectibles =
                NullSafe.list(NullSafe.get(raw::getCollectibles, null));

        return collectibles.stream()
                .map(this::convert)
                .toList();
    }

    /**
     * Raw → Tidy 변환
     */
    private CollectibleTidyDto convert(CollectibleDto c) {
        CollectibleTidyDto dto = new CollectibleTidyDto();

        dto.setType(NullSafe.get(c::getType, ""));
        dto.setIcon(NullSafe.get(c::getIcon, ""));
        dto.setPoint(NullSafe.get(c::getPoint, 0));
        dto.setMaxPoint(NullSafe.get(c::getMaxPoint, 0));

        // 세부 포인트 목록
        List<CollectiblePointTidyDto> details =
                NullSafe.list(c.getCollectiblePoints())
                        .stream()
                        .map(this::convertPoint)
                        .toList();
        dto.setDetails(details);

        return dto;
    }

    /**
     * 세부 포인트 변환
     */
    private CollectiblePointTidyDto convertPoint(CollectiblePointDto p) {
        CollectiblePointTidyDto dto = new CollectiblePointTidyDto();

        dto.setName(NullSafe.get(p::getPointName, ""));
        dto.setPoint(NullSafe.get(p::getPoint, 0));
        dto.setMaxPoint(NullSafe.get(p::getMaxPoint, 0));

        return dto;
    }
}