package com.example.ListArk.dto.raw.armory.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ArmoryCardDto {

    @JsonProperty("Cards")
    private List<CardDto> cards;

    @JsonProperty("Effects")
    private List<CardEffectDto> effects;
}
