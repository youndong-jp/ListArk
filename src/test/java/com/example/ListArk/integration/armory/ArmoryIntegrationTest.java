package com.example.ListArk.integration.armory;

import com.example.ListArk.support.BaseIntegrationTest;
import com.example.ListArk.support.MockLostArkServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

/**
 * Armory Tidy API 통합 테스트 (Simplified)
 * - 테스트 데이터 파일 없이 동작
 * - 핵심 시나리오만 검증
 */
@DisplayName("Armory 통합 테스트 (Simplified)")
class ArmoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockLostArkServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer.start();
        mockServer.reset();
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    // ================================================================
    // 1. 404 에러 테스트
    // ================================================================

    @Test
    @DisplayName("404 에러 - 존재하지 않는 캐릭터")
    void 존재하지_않는_캐릭터_404() {
        // given
        String invalidName = "존재하지않는캐릭터";
        mockServer.stub404(invalidName);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/characters/{name}/armory",
                String.class,
                invalidName
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ================================================================
    // 2. 500 에러 테스트
    // ================================================================

    @Test
    @DisplayName("500 에러 - Lost Ark API 장애 시 재시도 후 실패")
    void LostArk_API_장애_500() {
        // given
        String characterName = "테스트캐릭터";
        mockServer.stub500(characterName);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/characters/{name}/armory",
                String.class,
                characterName
        );

        // then
        // Retry 3회 후에도 500 에러
        assertThat(response.getStatusCode()).isIn(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    // ================================================================
    // 3. TODO: 정상 조회 테스트 (테스트 데이터 필요)
    // ================================================================

    @Test
    @Disabled("테스트 데이터 파일 생성 후 활성화")
    @DisplayName("Armory 전체 조회 - 정상 응답")
    void Armory_전체_조회_성공() {
        // TODO: test-data/armory/valid-armory.json 생성 후 활성화
    }

    @Test
    @Disabled("테스트 데이터 파일 생성 후 활성화")
    @DisplayName("Profile 조회 - 필드 검증")
    void Profile_조회_필드_검증() {
        // TODO: test-data/profile/valid-profile.json 생성 후 활성화
    }

    @Test
    @Disabled("테스트 데이터 파일 생성 후 활성화")
    @DisplayName("Retry 성공 - 첫 요청 503, 두 번째 요청 성공")
    void Retry_후_성공() {
        // TODO: test-data/armory/valid-armory.json 생성 후 활성화
    }

    @Test
    @Disabled("테스트 데이터 파일 생성 후 활성화")
    @DisplayName("Null 안전성 - 일부 필드 null이어도 에러 없음")
    void Null_안전성_검증() {
        // TODO: test-data/armory/minimal-armory.json 생성 후 활성화
    }

    @Test
    @Disabled("테스트 데이터 파일 생성 후 활성화")
    @DisplayName("Raw → Tidy 매핑 검증 - 데이터 손실 없음")
    void Raw_Tidy_매핑_검증() {
        // TODO: test-data/profile/full-profile.json 생성 후 활성화
    }
}