package com.example.ListArk.Dto.raw.armory.engraving;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArkPassiveEffectDto {

    @JsonProperty("AbilityStoneLevel")
    private Integer abilityStoneLevel;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
