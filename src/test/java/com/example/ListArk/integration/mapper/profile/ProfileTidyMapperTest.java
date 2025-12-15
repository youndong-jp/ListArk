package com.example.ListArk.integration.mapper.profile;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.profile.ArmoryProfileDto;
import com.example.ListArk.dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.mapper.armory.profile.ProfileTidyMapper;
import com.example.ListArk.support.TestDataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProfileTidyMapperTest {

    @Autowired
    private ProfileTidyMapper mapper;

    @Autowired
    private TestDataLoader loader;

    @Test
    @DisplayName("Profile Raw → Tidy 매핑 성공")
    void raw_to_tidy_success() {

        // 1) Raw JSON 로드
        String json = loader.loadProfileJson("valid-profile");

        // 2) Raw DTO 로 변환
        ArmoryProfileDto rawProfile = loader.parseJson(json, ArmoryProfileDto.class);

        ArmoryDto armory = new ArmoryDto();
        armory.setArmoryProfile(rawProfile);

        // 3) Tidy 변환 수행
        ProfileTidyDto dto = mapper.toTidy(armory);

        // 4) 필드 검증
        assertThat(dto).isNotNull();
        assertThat(dto.getCharacterName()).isNotEmpty();
        assertThat(dto.getCharacterClass()).isNotEmpty();
        assertThat(dto.getServerName()).isNotEmpty();

        // 5) Stats 매핑 (Array → Map)
        assertThat(dto.getStats()).isNotNull();
        assertThat(dto.getStats()).isNotEmpty();

        // 6) Tendencies 매핑
        assertThat(dto.getTendencies()).isNotNull();
        assertThat(dto.getTendencies()).isNotEmpty();

        System.out.println("Profile tidy result = " + dto);
    }

    @Test
    @DisplayName("null 입력 시 빈 ProfileTidyDto 반환")
    void null_safe_check() {
        ProfileTidyDto dto = mapper.toTidy(null);

        assertThat(dto).isNotNull();
        assertThat(dto.getCharacterName()).isBlank();
        assertThat(dto.getStats()).isEmpty();
    }

}
