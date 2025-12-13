package com.example.ListArk.support;

import com.example.ListArk.dto.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 * 통합 테스트 공통 기반 클래스
 * - TestRestTemplate 자동 주입
 * - 공통 헬퍼 메서드 제공
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    /**
     * GET 요청 헬퍼 - ParameterizedTypeReference 사용
     */
    protected <T> ResponseEntity<T> getWithType(
            String url,
            ParameterizedTypeReference<T> responseType,
            Object... uriVariables
    ) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType,
                uriVariables
        );
    }

    /**
     * GET 요청 헬퍼 - 간단한 타입 (Class<T>)
     */
    protected <T> ResponseEntity<T> get(
            String url,
            Class<T> responseType,
            Object... uriVariables
    ) {
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }

    /**
     * 캐릭터명으로 Armory 전체 조회
     */
    protected String getArmoryUrl(String characterName) {
        return String.format("/api/characters/%s/armory", characterName);
    }

    /**
     * 캐릭터명으로 Profile 조회
     */
    protected String getProfileUrl(String characterName) {
        return String.format("/api/characters/%s/profile", characterName);
    }

    /**
     * 캐릭터명으로 Equipment 조회
     */
    protected String getEquipmentUrl(String characterName) {
        return String.format("/api/characters/%s/equipment", characterName);
    }

    /**
     * 테스트용 실제 캐릭터명 (Lost Ark API 호출용)
     * TODO: 실제 존재하는 캐릭터명으로 변경
     */
    protected static final String VALID_CHARACTER = "니나브";

    /**
     * 테스트용 존재하지 않는 캐릭터명
     */
    protected static final String INVALID_CHARACTER = "존재하지않는캐릭터명123456789";

    /**
     * 테스트용 신규/저렙 캐릭터명 (일부 데이터 없음)
     * TODO: 실제 존재하는 저렙 캐릭터명으로 변경
     */
    protected static final String LOW_LEVEL_CHARACTER = "신규캐릭터";
}