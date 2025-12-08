package com.example.ListArk.dto.raw.armory.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CardEffectDto {

    @JsonProperty("Index")
    private int index;

    @JsonProperty("CardSlots")
    private List<Integer> cardSlots;

    @JsonProperty("Items")
    private List<CardEffectItemDto> items;
}
