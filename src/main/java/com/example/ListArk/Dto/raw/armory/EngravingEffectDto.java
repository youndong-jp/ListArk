package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EngravingEffectDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
