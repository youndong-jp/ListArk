package com.example.ListArk.controller;

import com.example.ListArk.config.annotation.CommonErrorResponses;
import com.example.ListArk.dto.common.ApiResponse;
import com.example.ListArk.dto.tidy.armory.*;
import com.example.ListArk.dto.tidy.armory.arkgrid.ArkGridTidyDto;
import com.example.ListArk.dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.dto.tidy.armory.collectible.CollectibleTidyDto;
import com.example.ListArk.dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.dto.tidy.armory.profile.ProfileTidyDto;

import com.example.ListArk.service.tidy.ArmoryTidyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
@Tag(name = "Tidy Armory API", description = "정리된(Tidy) 형식의 Armory 데이터를 제공하는 API")
public class ArmoryTidyController {

    private final ArmoryTidyService armoryTidyService;

    // 통합 조회
    @Operation(
            summary = "Armory 통합 Tidy 조회",
            description = "프로필 / 장비 / 각인 / 카드 / 전투 스킬 등 Armory 전체 데이터를 통합 Tidy 형식으로 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "정상 조회",
            content = @Content(schema = @Schema(implementation = ArmoryTidyDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/{name}/armory")
    public ApiResponse<ArmoryTidyDto> getArmory(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.toTidy(name));
    }

    @Operation(summary = "프로필 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/profile")
    public ApiResponse<ProfileTidyDto> getProfile(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getProfile(name));
    }

    @Operation(summary = "장비 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/equipment")
    public ApiResponse<List<EquipmentTidyDto>> getEquipment(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getEquipment(name));
    }

    @Operation(summary = "보석 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/gems")
    public ApiResponse<GemTidyDto> getGems(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getGems(name));
    }

    @Operation(summary = "각인 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/engravings")
    public ApiResponse<EngravingTidyDto> getEngravings(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getEngravings(name));
    }

    @Operation(summary = "아바타 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/avatars")
    public ApiResponse<List<AvatarTidyDto>> getAvatars(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getAvatars(name));
    }

    @Operation(summary = "전투 스킬 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/combat-skills")
    public ApiResponse<List<CombatSkillTidyDto>> getCombatSkills(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getCombatSkills(name));
    }

    @Operation(summary = "카드 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/cards")
    public ApiResponse<CardTidyDto> getCards(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getCards(name));
    }

    @Operation(summary = "모험물 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/collectibles")
    public ApiResponse<List<CollectibleTidyDto>> getCollectibles(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getCollectibles(name));
    }

    @Operation(summary = "증명의 전장 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/colosseum")
    public ApiResponse<ColosseumTidyDto> getColosseum(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getColosseum(name));
    }

    @Operation(summary = "아크 패시브 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/ark-passive")
    public ApiResponse<ArkPassiveTidyDto> getArkPassive(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getArkPassive(name));
    }

    @Operation(summary = "아크 그리드 Tidy 조회")
    @CommonErrorResponses
    @GetMapping("/{name}/ark-grid")
    public ApiResponse<ArkGridTidyDto> getArkGrid(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getArkGrid(name));
    }
}
