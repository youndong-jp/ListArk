package com.example.ListArk.dto.tidy.armory.combatskill;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "스킬 트라이포드 Tidy DTO")
public class TripodTidyDto {

    @Schema(description = "트라이포드 티어 (1~3)", example = "2")
    private int tier;

    @Schema(description = "티어 내 슬롯 번호 (1~3)", example = "1")
    private int slot;

    @Schema(description = "트라이포드 이름", example = "약점 포착")
    private String name;

    @Schema(description = "해당 트라이포드 선택 여부", example = "true")
    private boolean selected;
}
