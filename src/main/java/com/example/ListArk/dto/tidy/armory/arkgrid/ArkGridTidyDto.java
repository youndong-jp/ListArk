package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "아크 그리드 전체 Tidy 정보 DTO")
public class ArkGridTidyDto {

    @Schema(description = "아크 그리드 슬롯 정보 목록")
    private List<ArkGridSlotTidyDto> slots;

    @Schema(description = "아크 그리드 효과 목록 (전체 효과 레벨 정보)")
    private List<ArkGridEffectTidyDto> effects;
}
