package com.example.ListArk.dto.tidy.armory.collectible;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Tidy 모험물 상세 포인트 정보 DTO")
public class CollectiblePointTidyDto {

    @Schema(description = "모험물 이름", example = "거인의 심장")
    private String name;

    @Schema(description = "획득 포인트", example = "8")
    private int point;

    @Schema(description = "최대 포인트", example = "15")
    private int maxPoint;
}
