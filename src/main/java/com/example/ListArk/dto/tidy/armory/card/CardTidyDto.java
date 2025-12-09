package com.example.ListArk.dto.tidy.armory.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 카드 정보 DTO (장착 카드 + 세트 효과)")
public class CardTidyDto {

    @Schema(
            description = "장착 카드 리스트 (0~5 슬롯)",
            example = "[{\"slot\":0,\"name\":\"니나브\",\"grade\":\"전설\",\"awakeCount\":5}]"
    )
    private List<CardSimpleDto> cards;

    @Schema(
            description = "발동된 카드 세트 효과 목록",
            example = "[\"남겨진 바람의 절벽 (12각성)\", \"세 구원의 빛 (6각성)\"]"
    )
    private List<String> setEffects;
}
