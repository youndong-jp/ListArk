package com.example.ListArk.dto.tidy.armory.gem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "단일 보석 정보 DTO")
public class GemSimpleDto {

    @Schema(description = "보석이 장착된 슬롯 번호", example = "1")
    private int slot;

    @Schema(description = "보석 종류 (홍염/멸화,겁화/작열)", example = "겁화")
    private String type;

    @Schema(description = "보석 레벨", example = "8")
    private int level;

    @Schema(description = "보석 등급", example = "유물")
    private String grade;

    @Schema(description = "보석 아이콘 URL", example = "https://cdn.lostark.game/gem/red_7.png")
    private String icon;
}
