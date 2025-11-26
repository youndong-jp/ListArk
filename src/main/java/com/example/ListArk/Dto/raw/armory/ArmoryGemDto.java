package com.example.ListArk.Dto.raw.armory;

import lombok.Data;
import java.util.List;

@Data
public class    ArmoryGemDto {
    private List<GemEffectDto> effects;
    private List<GemDto> gems;
}
