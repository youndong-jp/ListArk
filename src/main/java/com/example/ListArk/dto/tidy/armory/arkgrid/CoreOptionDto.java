package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아크 그리드 코어 옵션 정보 DTO")
public class CoreOptionDto {

    @Schema(description = "포인트 기준", example = "20")
    private int point;

    @Schema(description = "코어 옵션 설명", example = "마력 방출 중 적에게 주는 피해가 2.0% 증가한다.")
    private String description;

    @Schema(description = "옵션 타입 (damage / cooldown / special)", example = "damage")
    private String type;

    @Schema(description = "수치 값", example = "2.0")
    private Double value;
}
