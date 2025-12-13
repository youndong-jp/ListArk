package com.example.ListArk.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Lost Ark API Mock 서버
 * - WireMock 기반
 * - 테스트용 응답 제공
 * - 실제 API 의존성 제거
 *
 * 사용법:
 * 1. mockServer.start()
 * 2. mockServer.stubArmory(characterName, jsonData)
 * 3. 테스트 실행
 * 4. mockServer.stop()
 */
@Component
public class MockLostArkServer {

    private WireMockServer wireMockServer;
    private static final int MOCK_PORT = 8089;

    /**
     * Mock 서버 시작
     */
    public void start() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new WireMockServer(MOCK_PORT);
            wireMockServer.start();
            WireMock.configureFor("localhost", MOCK_PORT);
        }
    }

    /**
     * Mock 서버 중지
     */
    public void stop() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    /**
     * Mock 서버 초기화 (모든 stub 제거)
     */
    public void reset() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.resetAll();
        }
    }

    /**
     * Armory 전체 조회 Mock 응답 설정
     */
    public void stubArmory(String characterName, String jsonResponse) {
        stubFor(get(urlPathMatching("/armories/characters/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)
                )
        );
    }

    /**
     * Profile 조회 Mock 응답 설정
     */
    public void stubProfile(String characterName, String jsonResponse) {
        stubFor(get(urlPathEqualTo("/armories/characters/" + characterName + "/profiles"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)
                )
        );
    }

    /**
     * 404 Not Found Mock 응답 설정
     */
    public void stub404(String characterName) {
        stubFor(get(urlPathMatching("/armories/characters/.*"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Character not found\"}")
                )
        );
    }
    /**
     * 500 Internal Server Error Mock 응답 설정
     */
    public void stub500(String characterName) {
        stubFor(get(urlPathMatching("/armories/characters/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Internal Server Error\"}")
                )
        );
    }

    /**
     * 503 Service Unavailable Mock 응답 설정 (Retry 테스트용)
     */
    public void stub503ThenSuccess(String characterName, String jsonResponse) {
        stubFor(get(urlPathMatching("/armories/characters/" + characterName + ".*"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("Started")
                .willReturn(aResponse().withStatus(503))
                .willSetStateTo("First Retry")
        );

        stubFor(get(urlPathMatching("/armories/characters/" + characterName + ".*"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("First Retry")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)
                )
        );
    }

    /**
     * Timeout Mock 응답 설정
     */
    public void stubTimeout(String characterName) {
        stubFor(get(urlPathMatching("/armories/characters/" + characterName + ".*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(10000) // 10초 지연 → timeout 발생
                )
        );
    }
}