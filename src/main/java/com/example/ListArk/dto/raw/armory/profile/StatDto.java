package com.example.ListArk.dto.raw.armory.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class StatDto {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Tooltip")
    private List<String> tooltip;
}

