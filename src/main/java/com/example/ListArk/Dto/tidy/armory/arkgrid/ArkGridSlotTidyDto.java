package com.example.ListArk.Dto.tidy.armory.arkgrid;

import lombok.Data;

import java.util.List;

@Data
public class ArkGridSlotTidyDto {

    private int slot;
    private String icon;
    private String name;
    private int point;
    private String grade;

    private String coreType;
    private int willpower;
    private List<CoreOptionDto> options;
    private List<ArkGridGemTidyDto> gems;
}

