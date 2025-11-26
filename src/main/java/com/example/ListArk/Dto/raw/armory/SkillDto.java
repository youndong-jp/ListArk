package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SkillDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("IsAwakening")
    private boolean isAwakening;

    @JsonProperty("Tripods")
    private List<TripodDto> tripods;
}
