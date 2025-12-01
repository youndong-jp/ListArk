package com.example.ListArk.Dto.tidy.armory.card;

import lombok.Data;
import java.util.List;

@Data
public class CardTidyDto {

    private List<CardSimpleDto> cards;    // 카드 6장
    private List<String> setEffects;      // ["세구빛 18각", "남바절 12각"]

}
