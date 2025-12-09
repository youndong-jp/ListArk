package com.example.ListArk.dto.tidy.armory.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "단일 장착 카드 정보 DTO")
public class CardSimpleDto {

    @Schema(description = "카드 슬롯 번호 (0~5)", example = "2")
    private int slot;

    @Schema(description = "카드 이름", example = "니나브")
    private String name;

    @Schema(description = "카드 아이콘 URL", example = "https://cdn.lostark.game/card/ninave.png")
    private String icon;

    @Schema(description = "카드 등급", example = "전설")
    private String grade;

    @Schema(description = "현재 각성 단계", example = "5")
    private int awakeCount;

    @Schema(description = "최대 각성 단계", example = "5")
    private int awakeTotal;

}
