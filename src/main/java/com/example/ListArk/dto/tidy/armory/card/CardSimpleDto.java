package com.example.ListArk.dto.tidy.armory.card;

import lombok.Data;

@Data
public class CardSimpleDto {

    private int slot;           // 카드 슬롯 번호 (0~5)
    private String name;        // 카드 이름
    private String icon;        // 카드 아이콘 URL
    private String grade;       // 등급 (전설, 영웅 등)
    private int awakeCount;     // 현재 각성 단계
    private int awakeTotal;     // 최대 각성 단계
    private String tooltip;     // 카드 상세 정보 (HTML)
}