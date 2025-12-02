package com.example.ListArk.Dto.tidy.armory.gem;

import lombok.Data;

@Data
public class GemEffectTidyDto {

    private String text;        // 예: "일격필살 피해 +20%"
    private String skill;       // 예: "일격필살"
    private String effectType;  // damage / cooldown
    private int value;          // 20
}
