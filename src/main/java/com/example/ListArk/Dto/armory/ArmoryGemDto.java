package com.example.ListArk.Dto.armory;

import lombok.Data;
import java.util.List;

@Data
public class    ArmoryGemDto {
    private List<GemEffectDto> effects;
    private List<GemDto> gems;
}
