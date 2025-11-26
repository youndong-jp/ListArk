package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArmoryGemDto {

    @JsonProperty("Gems")
    private List<GemDto> gems;

    @JsonProperty("Effects")
    private GemEffectDto effects;
}
