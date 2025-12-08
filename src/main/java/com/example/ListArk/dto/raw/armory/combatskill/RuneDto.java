package com.example.ListArk.dto.raw.armory.combatskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RuneDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("Tooltip")
    private String tooltip;
}
