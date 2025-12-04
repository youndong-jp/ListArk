package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;
import java.util.List;

@Data
public class ArkGridGemTidyDto {
    private int slot;
    private String icon;
    private boolean active;
    private String grade;

    // ✨ tooltip 파싱 결과
    private String name;            // "질서의 젬 : 안정"
    private String gemType;         // "질서"
    private int gemPoint;           // 16
    private int requiredWillpower;  // 3
    private int orderPoint;         // 5
    private List<GemEffectDto> effects;
}