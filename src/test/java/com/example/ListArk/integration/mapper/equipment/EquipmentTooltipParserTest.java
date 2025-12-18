package com.example.ListArk.integration.mapper.equipment;

import com.example.ListArk.dto.tidy.armory.equipment.*;
import com.example.ListArk.mapper.armory.equipment.EquipmentTooltipParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("EquipmentTooltipParser 테스트")
class EquipmentTooltipParserTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EquipmentTooltipParser parser = new EquipmentTooltipParser(objectMapper);

    // =====================================================================
    // 1. DTO 기본 안정성 (NullSafe 보장)
    // =====================================================================

    @Test
    @DisplayName("EquipmentTidyDto 기본 리스트 필드는 null이 아니다")
    void dto_list_not_null() {
        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThat(dto.getRestrictionInfoList()).isNotNull();
        assertThat(dto.getTradeInfoList()).isNotNull();
        assertThat(dto.getEffectList()).isNotNull();
        assertThat(dto.getSlotEffect()).isNotNull();
    }

    // =====================================================================
    // 2. JSON 파싱 안정성
    // =====================================================================

    @Test
    @DisplayName("Invalid JSON 입력 시 예외가 발생한다")
    void invalid_json() {
        String json = "{ invalid json ...";
        EquipmentTidyDto dto = new EquipmentTidyDto();

        assertThatThrownBy(() ->
                parser.parseAndSetTooltip(json, dto)
        ).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("빈 JSON ({}) 입력 시 DTO는 변경되지 않는다")
    void empty_json() throws Exception {
        EquipmentTidyDto dto = new EquipmentTidyDto();

        parser.parseAndSetTooltip("{}", dto);

        assertThat(dto.getRestrictionInfoList()).isEmpty();
        assertThat(dto.getTradeInfoList()).isEmpty();
        assertThat(dto.getEffectList()).isEmpty();
        assertThat(dto.getSlotEffect()).isEmpty();
    }

    // =====================================================================
    // 3. Element 구조 예외 처리
    // =====================================================================

    @Test
    @DisplayName("Element에 type이 없으면 해당 Element는 무시된다")
    void element_no_type() throws Exception {
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

    @Test
    @DisplayName("Element에 value가 없으면 해당 Element는 무시된다")
    void element_no_value() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle"
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        // value 자체가 없으므로 아무 필드도 채워지지 않음
        assertThat(dto.getItemLevel()).isNull();
        assertThat(dto.getEquipStatus()).isNull();
        assertThat(dto.getQualityValue()).isEqualTo(0); // int 기본값
    }

    // =====================================================================
    // 4. ItemTitle 파싱 (분기 커버 핵심 구간)
    // =====================================================================

    @Test
    @DisplayName("ItemTitle - 일부 필드만 있어도 정상 파싱된다")
    void item_title_partial_fields() throws Exception {
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

    @Test
    @DisplayName("ItemTitle - leftStr2 없으면 itemLevel은 null 유지")
    void item_title_without_leftStr2() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle",
                "value": {
                  "rightStr0": "장착 중"
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getItemLevel()).isNull();
        assertThat(dto.getEquipStatus()).isEqualTo("장착 중");
    }

    @Test
    @DisplayName("ItemTitle - rightStr0 없으면 equipStatus는 null 유지")
    void item_title_without_rightStr0() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle",
                "value": {
                  "leftStr2": "아이템 레벨 1540"
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getEquipStatus()).isNull();
        assertThat(dto.getItemLevel()).isEqualTo("아이템 레벨 1540");
    }

    @Test
    @DisplayName("ItemTitle - icon이 비어있으면 slotData.iconPath를 사용한다")
    void item_title_sets_icon_when_empty() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle",
                "value": {
                  "slotData": {
                    "iconPath": "icon/from/tooltip.png"
                  }
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        dto.setIcon("");

        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getIcon()).isEqualTo("icon/from/tooltip.png");
    }

    @Test
    @DisplayName("ItemTitle - icon이 이미 있으면 tooltip icon으로 덮어쓰지 않는다")
    void item_title_does_not_override_existing_icon() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "ItemTitle",
                "value": {
                  "slotData": {
                    "iconPath": "icon/from/tooltip.png"
                  }
                }
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        dto.setIcon("existing/icon.png");

        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getIcon()).isEqualTo("existing/icon.png");
    }

    // =====================================================================
    // 5. ItemPartBox 파싱
    // =====================================================================

    @Test
    @DisplayName("ItemPartBox - <BR> 태그가 개행 문자로 변환된다")
    void item_part_box_br_handling() throws Exception {
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

        ItemPartBoxData data = dto.getEffectList().get(0);

        assertThat(data.getTitle()).isEqualTo("기본 효과");
        assertThat(data.getContent()).isEqualTo("효과1\n효과2");
    }

    // =====================================================================
    // 6. IndentStringGroup 파싱
    // =====================================================================

    @Test
    @DisplayName("IndentStringGroup - 여러 contentStr이 순서대로 병합된다")
    void indent_string_group_merge_test() throws Exception {
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

        IndentStringData data = dto.getSlotEffect().get(0);

        assertThat(data.getTitle()).isEqualTo("초월 효과");
        assertThat(data.getText()).isEqualTo("효과 A\n효과 B");
    }

    // =====================================================================
    // 7. SingleTextBox / MultiTextBox
    // =====================================================================

    @Test
    @DisplayName("SingleTextBox - 제한 정보가 restrictionInfoList에 추가된다")
    void parse_single_text_box() throws Exception {
        String json = """
            {
              "Element_000": {
                "type": "SingleTextBox",
                "value": "<FONT>캐릭터 귀속</FONT>"
              }
            }
            """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        assertThat(dto.getRestrictionInfoList()).containsExactly("캐릭터 귀속");
    }

    @Test
    @DisplayName("MultiTextBox - 거래 정보가 tradeInfoList에 추가된다")
    void parse_multi_text_box() throws Exception {
        String json = """
                {
                  "Element_000": {
                    "type": "MultiTextBox",
                    "value": "|거래 가능|"
                  }
                }
                """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
    }
    @Test
    @DisplayName("NameTagBox 타입은 현재 아무 동작도 하지 않는다")
    void name_tag_box_is_ignored() throws Exception {
        String json = """
        {
          "Element_000": {
            "type": "NameTagBox",
            "value": "<FONT>아이템 이름</FONT>"
          }
        }
        """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        // 현재 NameTagBox는 아무 필드도 변경하지 않음
        assertThat(dto.getItemLevel()).isNull();
        assertThat(dto.getEffectList()).isEmpty();
    }
    @Test
    @DisplayName("알 수 없는 Element 타입은 무시된다 (default branch)")
    void unknown_type_is_ignored() throws Exception {
        String json = """
        {
          "Element_000": {
            "type": "UnknownType",
            "value": "???"
          }
        }
        """;

        EquipmentTidyDto dto = new EquipmentTidyDto();
        parser.parseAndSetTooltip(json, dto);

        // 예외 없이 그냥 무시되는지만 확인
        assertThat(dto.getEffectList()).isEmpty();
        assertThat(dto.getRestrictionInfoList()).isEmpty();
    }

}