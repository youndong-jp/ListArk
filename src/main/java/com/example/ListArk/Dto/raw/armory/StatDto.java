package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatDto {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Tooltip")
    private String tooltip;
}
