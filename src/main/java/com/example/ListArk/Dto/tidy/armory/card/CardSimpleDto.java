package com.example.ListArk.Dto.tidy.armory.card;

import lombok.Data;

@Data
public class CardSimpleDto {

    private String name;
    private int awakeCount;   // 각성 단계
    private int awakeTotal;   // 최대 각성 게이지
    private String grade;
    private String icon;
}
