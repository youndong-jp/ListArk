package com.example.ListArk.dto.tidy.armory.card;

import lombok.Data;
import java.util.List;

@Data
public class CardTidyDto {

    private List<CardSimpleDto> cards;
    private List<String> setEffects;

}
