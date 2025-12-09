package com.example.ListArk.dto.tidy.armory.arkpassive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "아크 패시브 전체 Tidy 정보 DTO")
public class ArkPassiveTidyDto {

    @Schema(description = "아크 패시브 활성화 여부", example = "true")
    private boolean active;

    @Schema(description = "아크 패시브 포인트 목록")
    private List<ArkPassivePointTidyDto> points;

    @Schema(description = "아크 패시브 효과 목록")
    private List<ArkPassiveEffectTidyDto> effects;
}