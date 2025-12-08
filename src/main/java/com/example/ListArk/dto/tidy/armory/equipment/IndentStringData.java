package com.example.ListArk.dto.tidy.armory.equipment;

import lombok.Data;

/**
 * 계층 구조를 가진 복잡한 효과 정보를 담는 DTO
 * IndentStringGroup 타입의 tooltip 데이터를 파싱한 결과
 *
 * 사용 예:
 * - 무기/방어구: 슬롯 효과 (초월)
 * - 방어구: 엘릭서 효과, 연성 추가 효과
 * - 어빌리티 스톤: 무작위 각인 효과
 */
@Data
public class IndentStringData {

    /**
     * 효과 제목
     * 예: "슬롯 효과[초월] 7단계 21"
     *     "[엘릭서]지혜의 엘릭서"
     *     "연성 추가 효과선각자 (2단계)"
     *     "무작위 각인 효과"
     */
    private String title;

    /**
     * 효과 상세 내용 (줄바꿈으로 구분된 여러 줄)
     * 예: "무기 공격력 +2940\n모든 장비에 적용된 총 126개\n5 - 공격력이 800 증가..."
     */
    private String text;
}