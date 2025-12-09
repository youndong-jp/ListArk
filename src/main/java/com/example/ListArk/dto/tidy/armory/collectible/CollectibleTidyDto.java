package com.example.ListArk.dto.tidy.armory.collectible;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 수집품 요약 정보 DTO")
public class CollectibleTidyDto {

    @Schema(description = "모험물 종류", example = "거인의 심장")
    private String type;

    @Schema(description = "모험물 아이콘 URL", example = "https://cdn.lostark.game/collectible/giant_heart.png")
    private String icon;

    @Schema(description = "총 획득 포인트", example = "8")
    private int point;

    @Schema(description = "총 최대 포인트", example = "15")
    private int maxPoint;

    @Schema(
            description = "상세 포인트 리스트",
            example = "[{\"name\":\"1단계 호감도 보상\",\"point\":1,\"maxPoint\":1}]"
    )
    private List<CollectiblePointTidyDto> details;
}
