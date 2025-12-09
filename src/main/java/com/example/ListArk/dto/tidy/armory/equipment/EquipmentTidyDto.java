package com.example.ListArk.dto.tidy.armory.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 장비 아이템 정보를 담는 DTO (Tidy Format)
 */
@Data
@Schema(description = "Tidy 장비 정보 DTO")
public class EquipmentTidyDto {

    // ========================================
    // 기본 장비 정보
    // ========================================

    @Schema(description = "장비 슬롯 위치", example = "무기")
    private String slot;

    @Schema(description = "장비 이름 (강화 단계 포함)", example = "+23 운명의 업화 한손검")
    private String name;

    @Schema(description = "장비 아이콘 이미지 URL", example = "https://cdn.lostark.game/item/icon_12345.png")
    private String icon;

    @Schema(description = "장비 등급", example = "고대")
    private String grade;

    @Schema(description = "아이템 레벨 정보", example = "아이템 레벨 1745 (티어 4)")
    private String itemLevel;

    // ========================================
    // Tooltip 파싱 데이터
    // ========================================

    @Schema(
            description = "품질 수치 (0~100). 장신구/스톤/팔찌는 -1",
            example = "92"
    )
    private int qualityValue;

    @Schema(
            description = "장착 상태 (장착중 또는 null)",
            example = "장착중"
    )
    private String equipStatus;

    @Schema(
            description = "제한/귀속 정보 리스트",
            example = "[\"홀리나이트 전용\", \"캐릭터 귀속됨\", \"[상급 재련] 40단계\"]"
    )
    private List<String> restrictionInfoList = new ArrayList<>();

    @Schema(
            description = "거래 관련 정보 리스트",
            example = "[\"거래 불가\"]"
    )
    private List<String> tradeInfoList = new ArrayList<>();

    @Schema(
            description = "효과 리스트 (기본 효과, 추가 효과, 연마 효과 등)",
            example =
                    "[" +
                            "{\"title\": \"기본 효과\", \"values\": [\"무기 공격력 +52664\"]}," +
                            "{\"title\": \"추가 효과\", \"values\": [\"치명 +1200\", \"특화 +900\"]}" +
                            "]"
    )
    private List<ItemPartBoxData> effectList = new ArrayList<>();

    @Schema(
            description = "슬롯 효과 리스트 (초월 / 엘릭서 / 각인 등)",
            example =
                    "[" +
                            "{\"type\": \"초월\", \"description\": \"+21초월 효과 적용\"}," +
                            "{\"type\": \"엘릭서\", \"description\": \"선각자 2단계 효과 적용\"}" +
                            "]"
    )
    private List<IndentStringData> slotEffect = new ArrayList<>();

    @Schema(
            description = "내구도 정보 (장신구/스톤/팔찌 등은 null)",
            example = "내구도 134 / 175"
    )
    private String durability;
}
