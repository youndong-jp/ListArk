package com.example.ListArk.Dto.raw.armory.arkgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArkGridGemDto {

    @JsonProperty("Index")
    private int index;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("Tooltip")
    private String tooltip;
}
