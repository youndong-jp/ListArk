package com.example.ListArk.Dto.tidy.armory.avatar;

import lombok.Data;

import java.util.List;

/**
 * 아바타 Tidy DTO
 * Raw 데이터 + Tooltip 파싱 정보를 통합한 최종 결과
 */
@Data
public class AvatarTidyDto {

    // ========================================
    // Raw DTO에서 직접 가져오는 기본 정보
    // ========================================

    /**
     * 아바타 타입
     * 예: "무기 아바타", "머리 아바타", "상의 아바타", "악기 아바타"
     */
    private String type;

    /**
     * 아바타 이름
     * 예: "예견된 영원 한손검 (귀속)", "엄중한 도약 머리"
     */
    private String name;

    /**
     * 아이콘 URL
     * 예: "https://cdn-lostark.game.onstove.com/efui_iconatlas/shop_icon/shop_icon_7266.png"
     */
    private String icon;

    /**
     * 등급
     * 예: "전설", "영웅", "희귀"
     */
    private String grade;

    /**
     * 세트 아바타 여부
     * true: 세트 아바타, false: 단품 아바타
     */
    private Boolean isSet;

    /**
     * 이너 아바타 여부
     * true: 이너 아바타, false: 아우터 아바타
     */
    private Boolean isInner;

    // ========================================
    // Tooltip에서 파싱한 추가 정보
    // ========================================

    /**
     * 전용 클래스 또는 계열
     * 예: "홀리나이트", "전사(남)계열"
     * (Element_002에서 "전용" 제거 후 추출)
     */
    private String exclusiveClass;

    /**
     * 귀속 여부 (캐릭터 또는 원정대)
     * true: 귀속됨, false: 귀속 안됨
     * (Element_003에서 "귀속" 키워드로 판단)
     */
    private Boolean isBound;

    /**
     * 거래 가능 여부
     * true: 거래 가능, false: 거래 불가
     * (Element_004, Element_009에서 "거래 불가" 키워드로 판단)
     */
    private Boolean isTradable;

    /**
     * 판매 가능 여부
     * true: 판매 가능, false: 판매 불가
     * (Element_009에서 "판매불가" 키워드로 판단)
     */
    private Boolean isSellable;

    /**
     * 분해 가능 여부
     * true: 분해 가능, false: 분해 불가
     * (Element_009에서 "분해불가" 키워드로 판단)
     */
    private Boolean isDecomposable;

    /**
     * 기본 효과 목록
     * 예: ["힘 +2.00%", "지능 +1.50%"]
     * (Element_005 ItemPartBox에서 추출)
     * null: 효과 없음
     */
    private List<String> effects;

    // ========================================
    // 성향 정보 (Element_007 SymbolString에서 추출)
    // ========================================

    /**
     * 지성
     * null: 해당 성향 없음
     */
    private Integer intellect;

    /**
     * 담력
     * null: 해당 성향 없음
     */
    private Integer courage;

    /**
     * 매력
     * null: 해당 성향 없음
     */
    private Integer charm;

    /**
     * 친절
     * null: 해당 성향 없음
     */
    private Integer kindness;
}