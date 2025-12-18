package com.example.ListArk.integration.mapper.avatar;

import com.example.ListArk.dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.mapper.armory.avatar.AvatarTooltipParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AvatarTooltipParser 단위 테스트")
class AvatarTooltipParserTest {

    private final AvatarTooltipParser parser = new AvatarTooltipParser();

    // -------------------------------------------------------------
    // SingleTextBox
    // -------------------------------------------------------------
    @Test
    @DisplayName("SingleTextBox → 전용 클래스 / 귀속 / 거래 플래그 파싱")
    void parse_single_text_box() {

        Map<String, Object> element = Map.of(
                "type", "SingleTextBox",
                "value", "<FONT>워로드 전용</FONT> | 귀속 | 거래 불가 | 판매불가 | 분해불가"
        );

        Map<String, Object> tooltip = Map.of("Element_000", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        assertThat(dto.getExclusiveClass()).isEqualTo("워로드");
        assertThat(dto.getIsBound()).isTrue();
        assertThat(dto.getIsTradable()).isFalse();
        assertThat(dto.getIsSellable()).isFalse();
        assertThat(dto.getIsDecomposable()).isFalse();
    }

    // -------------------------------------------------------------
    // MultiTextBox
    // -------------------------------------------------------------
    @Test
    @DisplayName("MultiTextBox → 거래/판매/분해 플래그 파싱")
    void parse_multi_text_box() {

        Map<String, Object> element = Map.of(
                "type", "MultiTextBox",
                "value", "판매불가 | 분해불가"
        );

        Map<String, Object> tooltip = Map.of("Element_001", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        assertThat(dto.getIsSellable()).isFalse();
        assertThat(dto.getIsDecomposable()).isFalse();
    }

    // -------------------------------------------------------------
    // ItemPartBox (효과)
    // -------------------------------------------------------------
    @Test
    @DisplayName("ItemPartBox → 효과 파싱")
    void parse_item_part_box() {

        Map<String, Object> valueMap = Map.of(
                "value1", "<FONT>효과</FONT>",
                "value2", "|치명타 적중 시 피해량 +10%|"
        );

        Map<String, Object> element = Map.of(
                "type", "ItemPartBox",
                "value", valueMap
        );

        Map<String, Object> tooltip = Map.of("Element_010", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        assertThat(dto.getEffects()).contains("치명타 적중 시 피해량 +10%");
    }

    // -------------------------------------------------------------
    // SymbolString → 성향 파싱
    // -------------------------------------------------------------
    @Test
    @DisplayName("SymbolString → 성향 포인트 파싱")
    void parse_symbol_string_tendency() {

        Map<String, Object> value = Map.of(
                "contentStr", "<FONT>지성 15 | 담력 20 | 매력 7 | 친절 3</FONT>"
        );

        Map<String, Object> element = Map.of(
                "type", "SymbolString",
                "value", value
        );

        Map<String, Object> tooltip = Map.of("Element_100", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        assertThat(dto.getIntellect()).isEqualTo(15);
        assertThat(dto.getCourage()).isEqualTo(20);
        assertThat(dto.getCharm()).isEqualTo(7);
        assertThat(dto.getKindness()).isEqualTo(3);
    }

    // -------------------------------------------------------------
    // Clean HTML test
    // -------------------------------------------------------------
    @Test
    @DisplayName("HTML 제거(clean) 검증")
    void clean_html() {
        Map<String, Object> element = Map.of(
                "type", "SingleTextBox",
                "value", "<FONT COLOR='red'>테스트&nbsp;문자 전용</FONT> "
        );

        Map<String, Object> tooltip = Map.of("Element_200", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        // clean된 text가 exclusiveClass 또는 bound 등에 반영되지는 않지만
        // HTML 제거 로직이 실행되는지 확인
        assertThat(dto.getExclusiveClass()).contains("테스트 문자");
    }

    // -------------------------------------------------------------
    // 잘못된 구조 무시
    // -------------------------------------------------------------
    @Test
    @DisplayName("툴팁 구조가 Map이 아닐 경우 무시")
    void ignore_invalid_structure() {

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData("NOT A MAP", dto);

        // 기본 거래 상태 유지(true)
        assertThat(dto.getIsTradable()).isTrue();
        assertThat(dto.getIsSellable()).isTrue();
    }
    // -------------------------------------------------------------
    // Description 단독 Parser 테스트
    // -------------------------------------------------------------
    @Test
    @DisplayName("Description → effects 파싱")
    void parse_description_to_effects() {

        Map<String, Object> element = Map.of(
                "type", "Description",
                "value", "<FONT COLOR='#FFD200'>힘 +5</FONT>"
        );

        Map<String, Object> tooltip = Map.of("Element_300", element);

        AvatarTidyDto dto = new AvatarTidyDto();

        parser.parseTooltipData(tooltip, dto);

        assertThat(dto.getEffects())
                .isNotNull()
                .contains("힘 +5");
    }

}
