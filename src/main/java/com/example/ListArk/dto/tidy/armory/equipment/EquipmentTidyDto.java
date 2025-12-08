package com.example.ListArk.dto.tidy.armory.equipment;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 장비 아이템 정보를 담는 DTO
 * Lost Ark API의 장비 tooltip 데이터를 프론트엔드에서 사용하기 쉽게 변환한 형태
 *
 * 지원 장비 타입:
 * - 무기/방어구 (무기, 투구, 상의, 하의, 장갑, 어깨)
 * - 장신구 (목걸이, 귀걸이, 반지)
 * - 어빌리티 스톤
 * - 팔찌
 * - 나침반, 부적, 보주
 */
@Data
public class EquipmentTidyDto {

    // ========================================
    // 기본 장비 정보
    // ========================================

    /**
     * 장비 슬롯 위치
     * 예: "무기", "투구", "상의", "목걸이" 등
     */
    private String slot;

    /**
     * 장비 이름 (강화 단계 포함)
     * 예: "+23 운명의 업화 한손검"
     */
    private String name;

    /**
     * 장비 아이콘 이미지 URL
     */
    private String icon;

    /**
     * 장비 등급
     * 예: "고대", "유물", "전설" 등
     */
    private String grade;

    /**
     * 아이템 레벨 정보
     * 예: "아이템 레벨 1745 (티어 4)"
     */
    private String itemLevel;

    // ========================================
    // Tooltip 파싱 데이터
    // ========================================

    /**
     * 품질 수치 (0~100)
     * 장신구/어빌리티 스톤/팔찌 등은 -1
     */
    private int qualityValue;

    /**
     * 장착 상태
     * 예: "장착중", null
     */
    private String equipStatus;

    /**
     * 제한 및 귀속 정보 리스트
     * 예: "홀리나이트 전용", "캐릭터 귀속됨", "[상급 재련] 40단계" 등
     */
    private List<String> restrictionInfoList = new ArrayList<>();

    /**
     * 거래 관련 정보 리스트
     * 예: "거래 불가", "거래 가능" 등
     */
    private List<String> tradeInfoList = new ArrayList<>();

    /**
     * 효과 리스트 (기본 효과, 추가 효과, 연마 효과 등)
     * 무기/방어구: 기본 효과, 추가 효과
     * 장신구: 기본 효과, 연마 효과, 아크 패시브
     * 팔찌: 팔찌 효과, 아크 패시브
     */
    private List<ItemPartBoxData> effectList = new ArrayList<>();

    /**
     * 슬롯 효과 리스트 (초월, 엘릭서, 연성, 각인 등)
     * 무기: [초월]
     * 방어구: [초월, 엘릭서, 연성 추가 효과]
     * 어빌리티 스톤: [무작위 각인 효과]
     */
    private List<IndentStringData> slotEffect = new ArrayList<>();

    /**
     * 내구도 정보
     * 예: "내구도 134 / 175"
     * 장신구/어빌리티 스톤/팔찌 등은 null
     */
    private String durability;
}