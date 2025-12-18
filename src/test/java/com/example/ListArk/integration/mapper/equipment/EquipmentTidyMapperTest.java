package com.example.ListArk.integration.mapper.equipment;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.mapper.armory.equipment.EquipmentTidyMapper;
import com.example.ListArk.mapper.armory.equipment.EquipmentTooltipParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipmentTidyMapperTest {

    // ---------------------------------------------------------------------
    // 기본 매핑 검증
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("Raw DTO 필드가 Tidy DTO로 정확히 매핑된다")
    void raw_to_tidy_field_mapping() {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto raw = new EquipmentDto();
        raw.setType("무기");
        raw.setName("빛의 대검");
        raw.setIcon("https://icon");
        raw.setGrade("유물");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(raw));

        List<EquipmentTidyDto> result = mapper.toTidy(armory);

        assertThat(result).hasSize(1);
        EquipmentTidyDto tidy = result.get(0);

        assertThat(tidy.getSlot()).isEqualTo("무기");
        assertThat(tidy.getName()).isEqualTo("빛의 대검");
        assertThat(tidy.getIcon()).isEqualTo("https://icon");
        assertThat(tidy.getGrade()).isEqualTo("유물");
    }

    // ---------------------------------------------------------------------
    // Tooltip parser 호출 여부
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("Tooltip 이 존재하면 parser 가 호출된다")
    void tooltip_parser_called() throws Exception {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto e = new EquipmentDto();
        e.setTooltip("{\"Element_000\": {\"type\": \"ItemTitle\"}}");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(e));

        mapper.toTidy(armory);

        verify(parser, times(1)).parseAndSetTooltip(anyString(), any());
    }

    @Test
    @DisplayName("Tooltip 이 null 또는 빈 문자열이면 parser 가 호출되지 않는다")
    void tooltip_null_or_empty() throws Exception {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto e1 = new EquipmentDto();
        e1.setTooltip(null);

        EquipmentDto e2 = new EquipmentDto();
        e2.setTooltip("");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(e1, e2));

        mapper.toTidy(armory);

        verify(parser, never()).parseAndSetTooltip(anyString(), any());
    }

    // ---------------------------------------------------------------------
    // Null / Empty 방어 로직
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("armoryDto 가 null 이면 빈 리스트 반환")
    void armory_null() {

        EquipmentTidyMapper mapper = new EquipmentTidyMapper(mock(EquipmentTooltipParser.class));

        List<EquipmentTidyDto> result = mapper.toTidy(null);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("armoryEquipment 가 null 이면 빈 리스트 반환")
    void equipment_list_null() {

        EquipmentTidyMapper mapper = new EquipmentTidyMapper(mock(EquipmentTooltipParser.class));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(null);

        assertThat(mapper.toTidy(armory)).isEmpty();
    }

    // ---------------------------------------------------------------------
    //  catch 분기 커버 테스트
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("개별 장비 Tooltip 파싱 중 오류가 발생해도 나머지는 정상 처리된다")
    void error_for_single_item_but_continues() throws Exception {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        // 첫 번째 장비 (parser 예외 발생)
        EquipmentDto bad = new EquipmentDto();
        bad.setType("무기");
        bad.setName("에러 장비");
        bad.setTooltip("{invalid}");

        // 두 번째 장비 (정상)
        EquipmentDto good = new EquipmentDto();
        good.setType("투구");
        good.setName("정상 장비");

        // parser 가 호출되면 무조건 예외
        doThrow(new RuntimeException("parse error"))
                .when(parser)
                .parseAndSetTooltip(anyString(), any());

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(bad, good));

        List<EquipmentTidyDto> result = mapper.toTidy(armory);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("정상 장비");
    }
}
