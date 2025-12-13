package com.example.ListArk.dto.tidy.armory.engraving;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "각인 상세 정보 DTO")
public class EngravingDetailDto {

    @Schema(
            description = "각인 이름",
            example = "원한"
    )
    private String name;

    @Schema(
            description = "최종 각인 레벨 (어빌리티 스톤 포함, 1~3)",
            example = "3"
    )
    private int level;

    @Schema(
            description = "어빌리티 스톤 각인 레벨 (없으면 null)",
            example = "2",
            nullable = true
    )
    private Integer stoneLevel;

    @Schema(
            description = "각인의 등급",
            example = "유물"
    )
    private String grade;

    @Schema(
            description = "정리된 각인 효과 설명 (HTML 태그 제거된 버전)",
            example = "보스 및 레이드 몬스터에게 주는 피해가 21% 증가하지만, 받는 피해가 20% 증가한다."
    )
    private String description;
}
