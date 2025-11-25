package com.example.ListArk.Dto.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TripodDto {

    @JsonProperty("Tier")
    private int tier;

    @JsonProperty("Slot")
    private int slot;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Level")
    private int level;
}
