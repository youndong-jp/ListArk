package com.example.ListArk.Dto.raw.armory.engraving;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EngravingEffectDto {

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
