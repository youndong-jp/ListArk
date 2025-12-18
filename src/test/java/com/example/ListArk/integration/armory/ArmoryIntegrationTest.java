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
        assertThat(response.getStatusCode()).isIn(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    // ================================================================
    // 3. 성공 케이스 (의도적으로 비활성화)
    // ================================================================

    @Disabled("""
        Armory 통합 성공 케이스는 다수의 외부 API(Profile, Equipment, Avatar, Gem 등)를
        동시에 호출하는 오케스트레이션 구조이므로,
        통합 테스트에서는 실패 시나리오만 검증한다.
        
        성공 흐름은 Mapper / Service 단위 테스트에서 보장한다.
        """)
    @Test
    @DisplayName("Armory 조회 성공 - 통합 성공 케이스 (비활성화)")
    void armory_success_disabled() {
        // intentionally disabled
    }
}
