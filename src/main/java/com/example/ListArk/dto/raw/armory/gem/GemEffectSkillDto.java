package com.example.ListArk.dto.raw.armory.gem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GemEffectSkillDto {

    @JsonProperty("GemSlot")
    private int gemSlot;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private List<String> description;

    @JsonProperty("Option")
    private String option;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Tooltip")
    private String tooltip;
}
