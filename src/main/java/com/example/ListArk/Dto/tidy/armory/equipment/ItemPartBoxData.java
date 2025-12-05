package com.example.ListArk.Dto.tidy.armory.equipment;

import lombok.Data;

/**
 * 장비의 효과 정보를 담는 DTO
 * ItemPartBox 타입의 tooltip 데이터를 파싱한 결과
 *
 * 사용 예:
 * - 무기/방어구: 기본 효과 (공격력, 방어력, 스탯), 추가 효과
 * - 장신구: 기본 효과 (스탯), 연마 효과, 아크 패시브 포인트
 * - 팔찌: 팔찌 효과 (스탯 + 특수 효과), 아크 패시브 포인트
 * - 어빌리티 스톤: 기본 효과 (체력), 세공 단계 보너스
 * - 나침반/부적: 추가 효과 (생활 컨텐츠 관련)
 * - 보주: 특수 효과 (스킬 효과)
 */
@Data
public class ItemPartBoxData {

    /**
     * 효과 제목
     * 예: "기본 효과", "추가 효과", "연마 효과", "아크 패시브 포인트 효과" 등
     */
    private String title;

    /**
     * 효과 내용 (줄바꿈 포함 가능)
     * 예: "무기 공격력 +151014"
     *     "물리 방어력 +8296\n마법 방어력 +9217\n힘 +78607"
     */
    private String content;
}