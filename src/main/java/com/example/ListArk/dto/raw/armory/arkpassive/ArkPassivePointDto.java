package com.example.ListArk.dto.raw.armory.arkpassive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArkPassivePointDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private int value;

    @JsonProperty("Tooltip")
    private String tooltip;

    @JsonProperty("Description")
    private String description;
}
