package com.example.ListArk.dto.raw.armory.arkgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArkGridSlotDto {

    @JsonProperty("Index")
    private int index;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Point")
    private int point;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("Tooltip")
    private String tooltip;

    @JsonProperty("Gems")
    private List<ArkGridGemDto> gems;
}
