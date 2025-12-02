package com.example.ListArk.Dto.tidy.armory.gem;

import lombok.Data;
import java.util.List;

@Data
public class GemTidyDto {

    private List<GemSimpleDto> gems;   // 보석 리스트
    private List<GemEffectTidyDto> effects;      // 보석 효과 리스트
}
