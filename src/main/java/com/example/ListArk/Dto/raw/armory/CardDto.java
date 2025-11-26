package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CardDto {

    @JsonProperty("Cards")
    private List<CardEffectDto> cards;

    @JsonProperty("Effects")
    private List<CardEffectDto> effects;
}
