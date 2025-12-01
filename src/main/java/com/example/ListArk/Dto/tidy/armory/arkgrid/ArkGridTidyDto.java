package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;
import java.util.List;

@Data
public class ArkGridTidyDto {

    private List<ArkGridSlotTidyDto> slots;
    private List<ArkGridEffectTidyDto> effects;
}
