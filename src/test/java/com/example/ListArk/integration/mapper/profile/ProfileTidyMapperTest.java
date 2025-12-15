package com.example.ListArk.integration.mapper.profile;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.profile.ArmoryProfileDto;
import com.example.ListArk.dto.raw.armory.profile.StatDto;
import com.example.ListArk.dto.raw.armory.profile.TendencyDto;
import com.example.ListArk.dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.mapper.armory.profile.ProfileTidyMapper;
import com.example.ListArk.support.TestDataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProfileTidyMapperTest {

    @Autowired
    private ProfileTidyMapper mapper;

    @Autowired
    private TestDataLoader loader;

    // ============================================================
    // 1. 정상 매핑 테스트
    // ============================================================

    @Test
    @DisplayName("Profile Raw → Tidy 매핑 성공")
    void raw_to_tidy_success() {
        String json = loader.loadProfileJson("valid-profile");
        ArmoryProfileDto rawProfile = loader.parseJson(json, ArmoryProfileDto.class);

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto).isNotNull();
        assertThat(dto.getCharacterName()).isNotEmpty();
        assertThat(dto.getCharacterClass()).isNotEmpty();
        assertThat(dto.getServerName()).isNotEmpty();

        assertThat(dto.getStats()).isNotEmpty();
        assertThat(dto.getTendencies()).isNotEmpty();
    }

    // ============================================================
    // 2. Stats 필터링 테스트
    // ============================================================

    @Test
    @DisplayName("Stats 필터링 - 전투 특성(COMBAT_STATS)만 남아야 한다")
    void stats_filtering_test() {

        StatDto stat1 = new StatDto();
        stat1.setType("치명");
        stat1.setValue("100");

        StatDto stat2 = new StatDto();
        stat2.setType("최대 생명력");
        stat2.setValue("999");

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        rawProfile.setStats(List.of(stat1, stat2));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getStats()).containsEntry("치명", 100);
        assertThat(dto.getStats()).doesNotContainKey("최대 생명력");
    }

    // ============================================================
    // 3. Tendencies 매핑 테스트
    // ============================================================

    @Test
    @DisplayName("Tendencies 매핑 - 지성/담력/매력/친절 값 확인")
    void tendencies_mapping_test() {

        TendencyDto t1 = new TendencyDto();
        t1.setType("지성");
        t1.setPoint(100);
        t1.setMaxPoint(1000);

        TendencyDto t2 = new TendencyDto();
        t2.setType("담력");
        t2.setPoint(200);
        t2.setMaxPoint(1000);

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        rawProfile.setTendencies(List.of(t1, t2));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getTendencies().get("지성")).isEqualTo(100);
        assertThat(dto.getTendencies().get("담력")).isEqualTo(200);
    }

    // ============================================================
    // 4. 숫자가 아닌 값 처리 테스트
    // ============================================================

    @Test
    @DisplayName("safeInt() - 숫자가 아닌 값은 0으로 처리된다")
    void invalid_value_returns_zero() {

        StatDto stat = new StatDto();
        stat.setType("치명");   // combat stat 유지
        stat.setValue("abc");  // 숫자 아님 → 0 처리

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        rawProfile.setStats(List.of(stat));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getStats().get("치명")).isEqualTo(0);
    }

    // ============================================================
    // 5. raw == null → 빈 DTO 반환
    // ============================================================

    @Test
    @DisplayName("null 입력 시 빈 ProfileTidyDto 반환")
    void null_safe_check() {
        ProfileTidyDto dto = mapper.toTidy(null);

        assertThat(dto).isNotNull();
        assertThat(dto.getCharacterName()).isBlank();
        assertThat(dto.getStats()).isEmpty();
        assertThat(dto.getTendencies()).isEmpty();
    }

    // ============================================================
    // 6. raw != null but profile == null
    // ============================================================

    @Test
    @DisplayName("ArmoryDto는 존재하지만 ArmoryProfileDto가 null → 빈 DTO 반환")
    void raw_not_null_but_profile_null_test() {

        ArmoryDto armory = new ArmoryDto(); // profile 없음

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto).isNotNull();
        assertThat(dto.getStats()).isEmpty();
        assertThat(dto.getTendencies()).isEmpty();
    }

    // ============================================================
    // 7. null 포함된 Stats 처리
    // ============================================================

    @Test
    @DisplayName("Stats 내부에 null 값이 포함돼도 NPE 없이 처리")
    void null_stats_handling_test() {

        StatDto stat = new StatDto(); // type, value 모두 null

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        rawProfile.setStats(List.of(stat));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getStats()).isEmpty();
    }

    // ============================================================
    // 8. null 포함된 Tendencies 처리
    // ============================================================

    @Test
    @DisplayName("Tendencies 내부에 null 포함돼도 안전하게 처리")
    void null_tendencies_handling_test() {

        TendencyDto t = new TendencyDto(); // type, point 모두 null

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        rawProfile.setTendencies(List.of(t));

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getTendencies()).isEmpty();
    }

    // ============================================================
    // 9. 빈 JSON 처리 테스트
    // ============================================================

    @Test
    @DisplayName("빈 RawProfileDto 입력 시 기본값 유지")
    void empty_json_basic_value_test() {

        ArmoryProfileDto rawProfile = new ArmoryProfileDto();
        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        ProfileTidyDto dto = mapper.toTidy(armory);

        assertThat(dto.getCharacterName()).isBlank();
        assertThat(dto.getCharacterClass()).isBlank();
        assertThat(dto.getGuildName()).isBlank();
        assertThat(dto.getCharacterLevel()).isZero();
        assertThat(dto.getStats()).isEmpty();
        assertThat(dto.getTendencies()).isEmpty();
    }
}
