package com.example.ListArk.integration.mapper.equipment;

import com.example.ListArk.dto.tidy.armory.equipment.*;
import com.example.ListArk.mapper.armory.equipment.EquipmentTooltipParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EquipmentTooltipParserTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EquipmentTooltipParser parser = new EquipmentTooltipParser(objectMapper);

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("DTO 기본 리스트가 null이 아닌지 확인 (NullSafe 검증)")
    void dto_list_not_null() {
        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThat(dto.getRestrictionInfoList()).isNotNull();
        assertThat(dto.getTradeInfoList()).isNotNull();
        assertThat(dto.getEffectList()).isNotNull();
        assertThat(dto.getSlotEffect()).isNotNull();
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("Invalid JSON 입력 시 예외가 발생해야 한다")
    void invalid_json() {
        String json = "{ invalid json ...";

        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThatThrownBy(() ->
                parser.parseAndSetTooltip(json, dto)
        ).isInstanceOf(Exception.class);
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("빈 JSON ({}) 입력 시 아무 변화 없어야 함")
    void empty_json() {
        String json = "{}";
        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThatCode(() ->
                parser.parseAndSetTooltip(json, dto)
        ).doesNotThrowAnyException();

        assertThat(dto.getEffectList()).isEmpty();
        assertThat(dto.getTradeInfoList()).isEmpty();
        assertThat(dto.getRestrictionInfoList()).isEmpty();
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("Element에 type이 없으면 skip 해야 한다")
    void element_no_type() throws Exception{
        String json = """
            {
              "Element_000": {
                "value": "Hello"
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getEffectList()).isEmpty();
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("Element에 value가 없으면 skip 해야 한다")
    void element_no_value() {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle"
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThatCode(() ->
                parser.parseAndSetTooltip(json, dto)
        ).doesNotThrowAnyException();

        assertThat(dto.getItemLevel()).isNull();
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("ItemTitle - 일부 필드만 존재해도 정상 처리")
    void item_title_partial_fields() throws Exception{
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle",
                "value": {
                  "qualityValue": 70
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getQualityValue()).isEqualTo(70);
        assertThat(dto.getItemLevel()).isNull();
        assertThat(dto.getEquipStatus()).isNull();
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("ItemPartBox - <BR> 태그가 개행으로 변환되는지 확인")
    void item_part_box_br_handling() throws Exception{
        String json = """
            {
              "Element_000": {
                "type": "ItemPartBox",
                "value": {
                  "Element_000": "기본 효과",
                  "Element_001": "효과1<BR>효과2"
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getEffectList()).hasSize(1);
        ItemPartBoxData data = dto.getEffectList().get(0);

        assertThat(data.getTitle()).isEqualTo("기본 효과");
        assertThat(data.getContent()).isEqualTo("효과1\n효과2");
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("IndentStringGroup - contentStr 여러 개가 순서대로 합쳐지는지 확인")
    void indent_string_group_merge_test() throws Exception{
        String json = """
            {
              "Element_000": {
                "type": "IndentStringGroup",
                "value": {
                  "Element_000": {
                    "topStr": "초월 효과",
                    "contentStr": {
                      "Element_000": { "contentStr": "효과 A" },
                      "Element_001": { "contentStr": "효과 B" }
                    }
                  }
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getSlotEffect()).hasSize(1);
        IndentStringData data = dto.getSlotEffect().get(0);

        assertThat(data.getTitle()).isEqualTo("초월 효과");
        assertThat(data.getText()).isEqualTo("효과 A\n효과 B");
    }

    // ---------------------------------------------------------------------
    @Test
    @DisplayName("ShowMeTheMoney - 내구도 정보 파싱 확인")
    void parse_show_me_the_money() throws Exception{
        String json = """
            {
              "Element_000": {
                "type": "ShowMeTheMoney",
                "value": "내구도 24/35"
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getDurability()).isEqualTo("내구도 24/35");
    }
}
