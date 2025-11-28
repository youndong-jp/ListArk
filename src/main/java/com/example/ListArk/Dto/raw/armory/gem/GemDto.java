package com.example.ListArk.Dto.raw.armory.gem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GemDto {

    @JsonProperty("Slot")
    private int slot;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("Tooltip")
    private String tooltip;
}
