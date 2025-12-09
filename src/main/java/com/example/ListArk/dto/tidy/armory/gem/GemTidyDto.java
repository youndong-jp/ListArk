package com.example.ListArk.dto.tidy.armory.gem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 보석 전체 정보 DTO")
public class GemTidyDto {

    @Schema(
            description = "보석 리스트 (슬롯별 장착된 단일 보석 정보)",
            example = "[{\"slot\":1, \"type\":\"겁화\", \"level\":10, \"grade\":\"고대\", \"icon\":\"https://cdn.lostark.game/gem/red_7.png\"}]"
    )
    private List<GemSimpleDto> gems;

    @Schema(
            description = "보석 효과 리스트 (스킬 효과 및 상세 설명)",
            example = "[{\"gemSlot\":1, \"name\":\"절정: 버스트 피해 증가\", \"description\":\"버스트 피해량 19% 증가\", \"icon\":\"https://cdn.lostark.game/gem/effect_red.png\"}]"
    )
    private List<GemEffectTidyDto> effects;
}
