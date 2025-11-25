package com.example.ListArk.Dto.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GemDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Tooltip")
    private String tooltip;
}
