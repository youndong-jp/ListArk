package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "아크 그리드 슬롯에 장착된 젬 Tidy 정보 DTO")
public class ArkGridGemTidyDto {

    @Schema(description = "젬이 장착된 슬롯 번호", example = "2")
    private int slot;

    @Schema(description = "젬 아이콘 URL", example = "https://cdn-lostark.game/gem/icon_001.png")
    private String icon;

    @Schema(description = "활성 여부 (슬롯 조건 충족 시 true)", example = "true")
    private boolean active;

    @Schema(description = "젬 등급", example = "전설")
    private String grade;

    // -----------------------------
    // Tooltip 파싱 데이터
    // -----------------------------

    @Schema(description = "젬 이름", example = "질서의 젬 : 안정")
    private String name;

    @Schema(description = "젬 타입 (질서 / 혼돈)", example = "질서")
    private String gemType;

    @Schema(description = "젬 포인트", example = "16")
    private int gemPoint;

    @Schema(description = "해당 젬의 의지력", example = "3")
    private int requiredWillpower;

    @Schema(description = "해당 젬 '질서/혼돈 포인트'", example = "5")
    private int orderPoint;

    @Schema(description = "젬 효과 목록")
    private List<GemEffectDto> effects;
}

