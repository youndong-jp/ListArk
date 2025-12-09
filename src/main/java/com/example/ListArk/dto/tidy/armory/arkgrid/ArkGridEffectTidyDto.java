package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아크 그리드 전체 효과 레벨 정보 DTO")
public class ArkGridEffectTidyDto {

    @Schema(description = "효과 이름", example = "낙인력")
    private String name;

    @Schema(description = "효과 레벨", example = "3")
    private int level;
}
