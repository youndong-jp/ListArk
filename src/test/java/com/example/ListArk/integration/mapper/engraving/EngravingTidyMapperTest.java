package com.example.ListArk.integration.mapper.engraving;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.engraving.ArkPassiveEffectDto;
import com.example.ListArk.dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingDetailDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.mapper.armory.engraving.EngravingTidyMapper;
import com.example.ListArk.mapper.armory.engraving.EngravingTooltipParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EngravingTidyMapperTest {

    @Autowired
    private EngravingTidyMapper mapper;

    // ============================================================
    // 1. 정상 매핑 (최종 레벨 로직 포함)
    // ============================================================
    @Test
    @DisplayName("Engraving Raw → Tidy 정상 매핑 (최종 레벨 계산 포함)")
    void raw_to_tidy_success() {

        ArkPassiveEffectDto e = new ArkPassiveEffectDto();
        e.setName("원한");
        e.setGrade("전설");
        e.setDescription("<FONT COLOR='#fff'>피해 증가</FONT>");
        e.setLevel(2);                 // base level
        e.setAbilityStoneLevel(3);     // stone level

        ArmoryEngravingDto rawEngraving = new ArmoryEngravingDto();
        rawEngraving.setArkPassiveEffects(List.of(e));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEngraving(rawEngraving);

        EngravingTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getEngravings()).hasSize(1);

        EngravingDetailDto d = dto.getEngravings().get(0);
        assertThat(d.getName()).isEqualTo("원한");
        assertThat(d.getGrade()).isEqualTo("전설");
        assertThat(d.getDescription()).isEqualTo("피해 증가");
        assertThat(d.getLevel()).isEqualTo(3); // max(2,3)
        assertThat(d.getStoneLevel()).isEqualTo(3);
    }

    // ============================================================
    // 2. raw == null → 빈 DTO
    // ============================================================
    @Test
    @DisplayName("raw == null → 빈 EngravingTidyDto 반환")
    void null_raw() {
        EngravingTidyDto dto = mapper.toTidy(null);

        assertThat(dto).isNotNull();
        assertThat(dto.getEngravings()).isEmpty();
    }

    // ============================================================
    // 3. engravingRoot == null → 빈 리스트
    // ============================================================
    @Test
    @DisplayName("ArmoryEngravingDto == null → 빈 리스트 반환")
    void null_engraving_root() {

        ArmoryDto armory = new ArmoryDto(); //

        EngravingTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getEngravings()).isEmpty();
    }

    // ============================================================
    // 4. ArkPassiveEffects == null → 빈 리스트
    // ============================================================
    @Test
    @DisplayName("ArkPassiveEffects == null → 빈 리스트 반환")
    void null_effect_list() {

        ArmoryEngravingDto rawEngraving = new ArmoryEngravingDto();
        rawEngraving.setArkPassiveEffects(null);

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEngraving(rawEngraving);

        EngravingTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getEngravings()).isEmpty();
    }

    // ============================================================
    // 5. 내부 값 null-safe 테스트
    // ============================================================
    @Test
    @DisplayName("Engraving 내부 값이 null여도 매핑되며 기본값 채워짐")
    void null_fields_inside() {

        ArkPassiveEffectDto e = new ArkPassiveEffectDto();
        e.setName(null);
        e.setGrade(null);
        e.setDescription(null);
        e.setLevel(0);
        e.setAbilityStoneLevel(null);

        ArmoryEngravingDto rawEngraving = new ArmoryEngravingDto();
        rawEngraving.setArkPassiveEffects(List.of(e));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryEngraving(rawEngraving);

        EngravingTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getEngravings()).hasSize(1);

        EngravingDetailDto d = dto.getEngravings().get(0);
        assertThat(d.getName()).isEqualTo("");
        assertThat(d.getGrade()).isEqualTo("");
        assertThat(d.getDescription()).isEqualTo("");
        assertThat(d.getLevel()).isEqualTo(0);
        assertThat(d.getStoneLevel()).isNull(); // stoneLevel 그대로 null 허용
    }
}
