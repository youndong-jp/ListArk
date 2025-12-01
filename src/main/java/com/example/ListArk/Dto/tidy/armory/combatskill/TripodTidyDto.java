package com.example.ListArk.Dto.tidy.armory.combatskill;

import lombok.Data;

@Data
public class TripodTidyDto {
    private int tier;       // 1~3
    private int slot;       // 해당 티어의 몇번째 선택인지
    private String name;
    private boolean selected;
}
