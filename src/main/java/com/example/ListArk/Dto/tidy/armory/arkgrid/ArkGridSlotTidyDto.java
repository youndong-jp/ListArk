package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;

import java.util.List;

@Data
public class ArkGridSlotTidyDto {

    private int index;
    private String icon;
    private String name;
    private int point;
    private String grade;
    private String tooltip;

    private List<ArkGridGemTidyDto> gems; // 슬롯 안의 보석들
}

