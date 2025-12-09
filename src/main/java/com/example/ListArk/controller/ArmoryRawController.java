package com.example.ListArk.controller;

import com.example.ListArk.client.raw.ArmoryRawClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/raw/characters")
@RequiredArgsConstructor
@Tag(name = "RAW Armory - 세부 조회", description = "Lost Ark Armory 개별 RAW 조회 API")
public class ArmoryRawController {

    private final ArmoryRawClient armoryRawClient;

    // ================================
    //  RAW 전체 조회 (별도 태그)
    // ================================
    @Tag(name = "RAW Armory - 전체 조회")
    @Operation(
            summary = "RAW Armory 전체 정보 조회",
            description = "Lost Ark Armory 전체 RAW JSON 데이터를 그대로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "캐릭터 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "API Key 인증 실패"),
            @ApiResponse(responseCode = "429", description = "요청 제한(Too Many Requests)"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/{name}/armory")
    public String getRawCharacterArmory(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArmory(name).block();
    }

    // ================================
    //  RAW 세부 조회
    // ================================

    @Operation(summary = "RAW 프로필 조회", description = "/profiles RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/profiles")
    public String getRawProfile(@PathVariable String name) {
        return armoryRawClient.getRawCharacterProfile(name).block();
    }

    @Operation(summary = "RAW 장비 조회", description = "/equipment RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/equipment")
    public String getRawEquipment(@PathVariable String name) {
        return armoryRawClient.getRawCharacterEquipment(name).block();
    }

    @Operation(summary = "RAW 보석 조회", description = "/gems RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/gems")
    public String getRawGems(@PathVariable String name) {
        return armoryRawClient.getRawCharacterGems(name).block();
    }

    @Operation(summary = "RAW 각인 조회", description = "/engravings RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/engravings")
    public String getRawEngravings(@PathVariable String name) {
        return armoryRawClient.getRawCharacterEngravings(name).block();
    }

    @Operation(summary = "RAW 아바타 조회", description = "/avatars RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/avatars")
    public String getRawAvatars(@PathVariable String name) {
        return armoryRawClient.getRawCharacterAvatars(name).block();
    }

    @Operation(summary = "RAW 전투 스킬 조회", description = "/combat-skills RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/combat-skills")
    public String getRawCombatSkills(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCombatSkills(name).block();
    }

    @Operation(summary = "RAW 카드 조회", description = "/cards RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/cards")
    public String getRawCards(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCards(name).block();
    }

    @Operation(summary = "RAW 증명의 전장 조회", description = "/colosseums RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/colosseums")
    public String getRawColosseums(@PathVariable String name) {
        return armoryRawClient.getRawCharacterColosseums(name).block();
    }

    @Operation(summary = "RAW 모험물 조회", description = "/collectibles RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/collectibles")
    public String getRawCollectibles(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCollectibles(name).block();
    }

    @Operation(summary = "RAW 아크 패시브 조회", description = "/arkpassive RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/arkpassive")
    public String getRawArkPassive(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArkPassive(name).block();
    }

    @Operation(summary = "RAW 아크 그리드 조회", description = "/arkgrid RAW JSON을 그대로 반환합니다.")
    @GetMapping("/{name}/arkgrid")
    public String getRawArkGrid(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArkGrid(name).block();
    }
}
