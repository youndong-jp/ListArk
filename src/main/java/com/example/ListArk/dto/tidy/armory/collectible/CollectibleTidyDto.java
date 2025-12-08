package com.example.ListArk.dto.tidy.armory.collectible;

import lombok.Data;

import java.util.List;

@Data
public class CollectibleTidyDto {

    private String type;
    private String icon;
    private int point;
    private int maxPoint;

    private List<CollectiblePointTidyDto> details;
}
