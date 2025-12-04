package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;

@Data
public class GemEffectDto {
    private String name;        // "아군 피해 강화", "공격력"
    private int level;          // 4, 2
    private double value;       // 0.21, 0.07
    private String description; // "아군 피해량 강화 효과 +0.21%"
}