package com.example.ListArk.dto.raw.armory.engraving;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EngravingDto {

    @JsonProperty("Slot")
    private int slot;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Tooltip")
    private String tooltip;
}
