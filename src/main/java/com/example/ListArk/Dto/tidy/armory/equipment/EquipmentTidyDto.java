package com.example.ListArk.Dto.tidy.armory.equipment;

import lombok.Data;

@Data
public class EquipmentTidyDto {

    private String slot;
    private String name;
    private String icon;
    private String grade;

    private String quality;   // 품질
    private String itemLevel;

    private String tooltip;
}
