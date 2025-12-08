package com.example.ListArk.dto.tidy.armory.arkpassive;

import lombok.Data;

import java.util.List;

@Data
public class ArkPassiveTidyDto {

    private boolean active;

    private List<ArkPassivePointTidyDto> points;

    private List<ArkPassiveEffectTidyDto> effects;
}
