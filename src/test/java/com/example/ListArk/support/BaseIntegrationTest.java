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
     * 캐릭터명 + 엔드포인트 조합 Helper
     */
    protected String api(String name, String path) {
        return String.format("/api/characters/%s/%s", name, path);
    }

    /**
     * 캐릭터명으로 Armory 전체 조회
     */
    protected String getArmoryUrl(String name) {
        return api(name, "armory");
    }

    /**
     * 캐릭터명으로 Profile 조회
     */
    protected String getProfileUrl(String name) {
        return api(name, "profile");
    }

    /**
     * 캐릭터명으로 Equipment 조회
     */
    protected String getEquipmentUrl(String name) {
        return api(name, "equipment");
    }

    /**
     * 캐릭터명으로 Avatar 조회
     */
    protected String getAvatarUrl(String name) {
        return api(name, "avatars");
    }

    /**
     * 캐릭터명으로 Engraving 정보 조회
     */
    protected String getEngravingUrl(String name) {
        return api(name, "engravings");
    }

    /**
     * 캐릭터명으로 Gem(보석) 정보 조회
     */
    protected String getGemsUrl(String name) {
        return api(name, "gems");
    }

    /**
     * 캐릭터명으로 Combat Skill 정보 조회
     */
    protected String getCombatSkillsUrl(String name) {
        return api(name, "combat-skills");
    }

    /**
     * 캐릭터명으로 Card & 세트 효과 조회
     */
    protected String getCardsUrl(String name) {
        return api(name, "cards");
    }

    /**
     * 캐릭터명으로 Collectibles(모코코, 섬의 마음 등) 조회
     */
    protected String getCollectiblesUrl(String name) {
        return api(name, "collectibles");
    }

    /**
     * 캐릭터명으로 Colosseum(PvP) 정보 조회
     */
    protected String getColosseumUrl(String name) {
        return api(name, "colosseum");
    }

    /**
     * 캐릭터명으로 Ark Passive 정보 조회
     */
    protected String getArkPassiveUrl(String name) {
        return api(name, "arkpassive");
    }

    /**
     * 캐릭터명으로 Ark Grid(코어/젬/효과) 조회
     */
    protected String getArkGridUrl(String name) {
        return api(name, "arkgrid");
    }

    protected static final String VALID_CHARACTER = "이성민화이팅1";
    protected static final String INVALID_CHARACTER = "존재하지않는캐릭터명123456789";

}