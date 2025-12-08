package com.example.ListArk.dto.raw.armory.engraving;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArmoryEngravingDto {

    @JsonProperty("Engravings")
    private List<EngravingDto> engravings;

    @JsonProperty("Effects")
    private List<EngravingEffectDto> effects;

    @JsonProperty("ArkPassiveEffects")
    private List<ArkPassiveEffectDto> arkPassiveEffects;
}
