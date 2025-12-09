package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "젬 효과 정보 DTO")
public class GemEffectDto {

    @Schema(description = "효과 이름", example = "아군 피해 강화")
    private String name;

    @Schema(description = "효과 레벨", example = "4")
    private int level;

    @Schema(description = "효과 값 (퍼센트 또는 수치)", example = "0.21")
    private double value;

    @Schema(description = "효과 설명", example = "아군 피해량 강화 효과 +0.21%")
    private String description;
}
