package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;

@Data
public class CoreOptionDto {
    private int point;           // 10, 14, 17, 18, 19, 20
    private String description;  // "마력 방출 중 적에게 주는 피해가 2.0% 증가한다."
    private String type;         // "damage" | "cooldown" | "special"
    private Double value;        // 2.0, 3.3, 8.0 등
}