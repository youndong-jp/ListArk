package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EngravingDto {

    @JsonProperty("Engravings")
    private List<EngravingEffectDto> engravings;

    @JsonProperty("Tooltip")
    private String tooltip;
}
