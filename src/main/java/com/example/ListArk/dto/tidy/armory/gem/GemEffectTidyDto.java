package com.example.ListArk.dto.tidy.armory.gem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "보석 효과 Tidy DTO")
public class GemEffectTidyDto {

    @Schema(description = "해당 효과가 적용된 보석 슬롯 번호", example = "1")
    private int gemSlot;

    @Schema(description = "스킬 또는 효과 이름", example = "절정: 버스트 피해 증가")
    private String name;

    @Schema(description = "보석이 부여하는 상세 효과 설명", example = "버스트 피해량 19% 증가")
    private String description;

    @Schema(description = "보석 효과 아이콘 URL", example = "https://cdn.lostark.game/gem/effect_red.png")
    private String icon;
}
