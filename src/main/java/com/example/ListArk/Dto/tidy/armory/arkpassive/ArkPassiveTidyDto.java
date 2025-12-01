package com.example.ListArk.Dto.tidy.armory.arkpassive;

import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveEffectTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassivePointTidyDto;
import lombok.Data;

import java.util.List;

@Data
public class ArkPassiveTidyDto {

    private boolean active;

    private List<ArkPassivePointTidyDto> points;

    private List<ArkPassiveEffectTidyDto> effects;
}
