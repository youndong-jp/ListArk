package com.example.ListArk.Dto.raw.armory.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardEffectItemDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
