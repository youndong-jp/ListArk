package com.example.ListArk.integration.mapper.equipment;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.mapper.armory.equipment.EquipmentTidyMapper;
import com.example.ListArk.mapper.armory.equipment.EquipmentTooltipParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EquipmentTidyMapperTest {

    @Test
    @DisplayName("기본 장비 필드 매핑 테스트")
    void basic_field_mapping() throws Exception{

        // given
        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto e = new EquipmentDto();
        e.setType("무기");
        e.setName("빛의 대검");
        e.setIcon("https://test.icon");
        e.setGrade("유물");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(e));

        // when
        List<EquipmentTidyDto> result = mapper.toTidy(armory);

        // then
        assertThat(result).hasSize(1);
        EquipmentTidyDto dto = result.get(0);

        assertThat(dto.getSlot()).isEqualTo("무기");
        assertThat(dto.getName()).isEqualTo("빛의 대검");
        assertThat(dto.getIcon()).isEqualTo("https://test.icon");
        assertThat(dto.getGrade()).isEqualTo("유물");

        verify(parser, never()).parseAndSetTooltip(any(), any());
    }

    @Test
    @DisplayName("Tooltip 있을 때 parser 가 호출되는지 확인")
    void tooltip_parser_called() throws Exception{

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto e = new EquipmentDto();
        e.setTooltip("{\"Element_000\": {\"type\": \"test\"}}");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(e));

        // when
        mapper.toTidy(armory);

        // then
        verify(parser, times(1)).parseAndSetTooltip(anyString(), any());
    }

    @Test
    @DisplayName("Tooltip이 null 또는 빈 문자열이면 parser 호출 X")
    void tooltip_null_or_empty() throws Exception{

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto e1 = new EquipmentDto();
        e1.setTooltip(null);

        EquipmentDto e2 = new EquipmentDto();
        e2.setTooltip("");

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(e1, e2));

        // when
        mapper.toTidy(armory);

        // then
        verify(parser, never()).parseAndSetTooltip(anyString(), any());
    }

    @Test
    @DisplayName("armoryDto가 null이면 빈 리스트 반환")
    void armory_null() {
        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        List<EquipmentTidyDto> result = mapper.toTidy(null);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("armoryEquipment가 null이면 빈 리스트 반환")
    void equipment_list_null() {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(null);

        List<EquipmentTidyDto> result = mapper.toTidy(armory);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("개별 장비 파싱 중 오류가 발생해도 나머지는 처리된다")
    void error_for_single_item_but_continues() throws Exception {

        EquipmentTooltipParser parser = mock(EquipmentTooltipParser.class);
        EquipmentTidyMapper mapper = new EquipmentTidyMapper(parser);

        EquipmentDto good = new EquipmentDto();
        good.setType("투구");

        EquipmentDto bad = mock(EquipmentDto.class);
        bad.setTooltip("{...}"); // parser 가 호출되도록

        // parser 가 예외를 던지게 설정
        doThrow(new RuntimeException("parse error"))
                .when(parser)
                .parseAndSetTooltip(anyString(), any());

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEquipment(List.of(good, bad));

        // when
        List<EquipmentTidyDto> result = mapper.toTidy(armory);

        // then
        assertThat(result).hasSize(2); // dto 생성은 됨
        // bad item 은 catch 처리 → mapper 가 죽지 않음
    }
}
