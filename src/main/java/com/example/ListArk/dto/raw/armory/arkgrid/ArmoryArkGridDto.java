package com.example.ListArk.dto.raw.armory.arkgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ArmoryArkGridDto {

    @JsonProperty("Slots")
    private List<ArkGridSlotDto> slots;

    @JsonProperty("Effects")
    private List<ArkGridEffectDto> effects;
}
