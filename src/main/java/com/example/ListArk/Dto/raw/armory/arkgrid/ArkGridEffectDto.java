package com.example.ListArk.Dto.raw.armory.arkgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArkGridEffectDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Tooltip")
    private String tooltip;
}
