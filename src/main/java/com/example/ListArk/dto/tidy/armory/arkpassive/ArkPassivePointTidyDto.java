package com.example.ListArk.dto.tidy.armory.arkpassive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아크 패시브 포인트 정보 DTO")
public class ArkPassivePointTidyDto {

    @Schema(description = "아크 패시브 계열 이름", example = "진화")
    private String name;

    @Schema(description = "해당 포인트 누적값", example = "120")
    private int value;
}
