package com.example.ListArk.Dto.raw.armory.arkpassive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArmoryArkPassiveDto {

    @JsonProperty("IsArkPassive")
    private boolean isArkPassive;

    @JsonProperty("Points")
    private List<ArkPassivePointDto> points;

    @JsonProperty("Effects")
    private List<ArkPassiveEffectDto> effects;
}
