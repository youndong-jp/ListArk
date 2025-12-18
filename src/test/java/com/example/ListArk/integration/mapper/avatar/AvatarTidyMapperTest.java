package com.example.ListArk.integration.mapper.avatar;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.mapper.armory.avatar.AvatarTidyMapper;
import com.example.ListArk.mapper.armory.avatar.AvatarTooltipParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AvatarTidyMapper 테스트")
class AvatarTidyMapperTest {

    private AvatarTidyMapper mapperWith(AvatarTooltipParser parser) {
        return new AvatarTidyMapper(parser, new ObjectMapper());
    }

    // ========================================================
    // 1. Null / 빈 리스트 처리
    // ========================================================
    // 목적:
    // - Mapper가 비정상 입력(raw / armoryAvatar)에 대해
    //   항상 안전하게 빈 리스트를 반환하는지 검증
    // ========================================================

    @Test
    @DisplayName("raw가 null이면 빈 리스트 반환")
    void raw_null() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        assertThat(mapper.toTidy(null)).isEmpty();
    }

    @Test
    @DisplayName("armoryAvatar가 null이면 빈 리스트 반환")
    void armory_avatar_null() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(null);

        assertThat(mapper.toTidy(raw)).isEmpty();
    }

    @Test
    @DisplayName("armoryAvatar가 빈 리스트면 빈 리스트 반환")
    void empty_avatar_list() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of());

        assertThat(mapper.toTidy(raw)).isEmpty();
    }

    // ========================================================
    // 2. Raw → Tidy 필드 매핑 검증
    // ========================================================
    // 목적:
    // - Raw AvatarDto의 기본 필드들이
    //   Tidy DTO로 정확히 복사/변환되는지 검증
    // ========================================================

    @Test
    @DisplayName("Raw DTO의 각 필드가 Tidy DTO로 정확히 매핑")
    void raw_to_tidy_field_mapping() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto raw = new AvatarDto();
        raw.setType("무기 아바타");
        raw.setName("천상의 검");
        raw.setIcon("https://icon.url");
        raw.setGrade("전설");
        raw.setSet(true);
        raw.setInner(false);

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryAvatar(List.of(raw));

        AvatarTidyDto tidy = mapper.toTidy(armory).get(0);

        assertThat(tidy.getType()).isEqualTo("무기 아바타");
        assertThat(tidy.getName()).isEqualTo("천상의 검");
        assertThat(tidy.getIcon()).isEqualTo("https://icon.url");
        assertThat(tidy.getGrade()).isEqualTo("전설");
        assertThat(tidy.getIsSet()).isTrue();
        assertThat(tidy.getIsInner()).isFalse();
    }

    @Test
    @DisplayName("null 필드가 있는 AvatarDto는 기본값으로 변환")
    void null_fields_default_values() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto avatar = new AvatarDto();

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getType()).isEmpty();
        assertThat(dto.getName()).isEmpty();
        assertThat(dto.getIcon()).isEmpty();
        assertThat(dto.getGrade()).isEmpty();
        assertThat(dto.getIsSet()).isFalse();
        assertThat(dto.getIsInner()).isFalse();
    }

    // ========================================================
    // 3. Tooltip 파서 호출 및 Fail-safe 검증
    // ========================================================
    // 목적:
    // - Tooltip JSON 존재 여부에 따라 parser 호출 여부
    // - JSON 파싱 실패 / 파서 예외 발생 시에도
    //   Mapper 흐름이 중단되지 않는지 검증
    // ========================================================

    @Test
    @DisplayName("Tooltip JSON이 있으면 parser 호출")
    void tooltip_parser_called() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto avatar = new AvatarDto();
        avatar.setTooltip("{\"Element_000\": \"test\"}");

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        mapper.toTidy(raw);

        verify(parser).parseTooltipData(anyMap(), any());
    }

    @Test
    @DisplayName("Tooltip이 빈 문자열이면 parser 호출하지 않음")
    void empty_tooltip() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto avatar = new AvatarDto();
        avatar.setTooltip("");

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        mapper.toTidy(raw);

        verify(parser, never()).parseTooltipData(any(), any());
    }

    @Test
    @DisplayName("Tooltip 파싱 실패해도 매핑은 계속된다")
    void tooltip_parse_fail_safe() {
        AvatarTooltipParser parser = mock(AvatarTooltipParser.class);
        doThrow(new RuntimeException("parse error"))
                .when(parser).parseTooltipData(anyMap(), any());

        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto avatar = new AvatarDto();
        avatar.setName("오류 아바타");
        avatar.setTooltip("{\"Element_000\": \"test\"}");

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getName()).isEqualTo("오류 아바타");
    }

    // ========================================================
    // 4. Tooltip 내용 → Tidy DTO 반영 (통합 검증)
    // ========================================================
    // 목적:
    // - 실제 AvatarTooltipParser와 연동하여
    //   Tooltip 내부 값이 Tidy DTO에 반영되는지 검증
    // ========================================================

    @Test
    @DisplayName("Description 값이 effects에 반영된다")
    void tooltip_description_mapping_to_effects() {
        AvatarTooltipParser parser = new AvatarTooltipParser();
        AvatarTidyMapper mapper = mapperWith(parser);

        String tooltipJson = """
        {
            "Element_001": {
                "type": "Description",
                "value": "<FONT>힘 +5</FONT>"
            }
        }
        """;

        AvatarDto avatar = new AvatarDto();
        avatar.setTooltip(tooltipJson);

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getEffects())
                .isNotNull()
                .anyMatch(e -> e.contains("힘 +5"));
    }

    @Test
    @DisplayName("Tooltip에 효과 타입이 없으면 effects는 빈 리스트")
    void effects_empty_when_no_effect_elements() {
        AvatarTooltipParser parser = new AvatarTooltipParser();
        AvatarTidyMapper mapper = mapperWith(parser);

        String tooltipJson = """
        {
            "Element_000": {
                "type": "SingleTextBox",
                "value": "귀속 | 거래 불가"
            }
        }
        """;

        AvatarDto avatar = new AvatarDto();
        avatar.setTooltip(tooltipJson);

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getEffects()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Description과 ItemPartBox 효과는 누적된다")
    void effects_accumulate_description_and_item_part_box() {
        AvatarTooltipParser parser = new AvatarTooltipParser();
        AvatarTidyMapper mapper = mapperWith(parser);

        String tooltipJson = """
        {
            "Element_001": {
                "type": "Description",
                "value": "<FONT>힘 +5</FONT>"
            },
            "Element_002": {
                "type": "ItemPartBox",
                "value": {
                    "v1": "<FONT>효과</FONT>",
                    "v2": "|치명타 적중 시 피해량 +10%|"
                }
            }
        }
        """;

        AvatarDto avatar = new AvatarDto();
        avatar.setTooltip(tooltipJson);

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getEffects())
                .hasSize(2)
                .anyMatch(e -> e.contains("힘 +5"))
                .anyMatch(e -> e.contains("치명타 적중 시 피해량 +10%"));
    }

    @Test
    @DisplayName("Tooltip이 없어도 기본 거래 플래그는 유지된다")
    void default_trade_flags_without_tooltip() {
        AvatarTooltipParser parser = new AvatarTooltipParser();
        AvatarTidyMapper mapper = mapperWith(parser);

        AvatarDto avatar = new AvatarDto();

        ArmoryDto raw = new ArmoryDto();
        raw.setArmoryAvatar(List.of(avatar));

        AvatarTidyDto dto = mapper.toTidy(raw).get(0);

        assertThat(dto.getIsTradable()).isTrue();
        assertThat(dto.getIsSellable()).isTrue();
        assertThat(dto.getIsDecomposable()).isTrue();
        assertThat(dto.getIsBound()).isFalse();
    }
}
