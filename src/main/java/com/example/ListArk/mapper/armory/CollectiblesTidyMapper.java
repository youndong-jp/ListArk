package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.Dto.raw.armory.collectibles.CollectiblePointDto;
import com.example.ListArk.Dto.tidy.armory.collectible.CollectiblePointTidyDto;
import com.example.ListArk.Dto.tidy.armory.collectible.CollectibleTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectiblesTidyMapper {

    public List<CollectibleTidyDto> toTidy(ArmoryDto raw) {

        if (raw == null || raw.getCollectibles() == null) {
            return List.of();
        }

        return raw.getCollectibles().stream()
                .map(this::convert)
                .toList();
    }

    /** Raw -> Tidy 변환 */
    private CollectibleTidyDto convert(CollectibleDto c) {

        CollectibleTidyDto dto = new CollectibleTidyDto();

        dto.setType(c.getType());
        dto.setIcon(c.getIcon());
        dto.setPoint(c.getPoint());
        dto.setMaxPoint(c.getMaxPoint());

        if (c.getCollectiblePoints() != null) {
            List<CollectiblePointTidyDto> detail = c.getCollectiblePoints().stream()
                    .map(this::convertPoint)
                    .toList();

            dto.setDetails(detail);
        } else {
            dto.setDetails(List.of());
        }

        return dto;
    }

    /** 세부 포인트 변환 */
    private CollectiblePointTidyDto convertPoint(CollectiblePointDto p) {

        CollectiblePointTidyDto dto = new CollectiblePointTidyDto();

        dto.setName(p.getPointName());
        dto.setPoint(p.getPoint());
        dto.setMaxPoint(p.getMaxPoint());

        return dto;
    }
}
