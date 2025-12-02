package com.example.ListArk.Dto.tidy.armory.card;

import lombok.Data;
import java.util.List;

@Data
public class CardTidyDto {

    private List<CardSimpleDto> cards;
    private List<String> setEffects;

}
