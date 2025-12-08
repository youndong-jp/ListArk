package com.example.ListArk.controller;

import com.example.ListArk.dto.tidy.armory.ArmoryTidyDto;
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
import com.example.ListArk.dto.common.ApiResponse;
import com.example.ListArk.service.tidy.ArmoryTidyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class ArmoryTidyController {

    private final ArmoryTidyService armoryTidyService;


    @GetMapping("/{name}/armory")
    public ApiResponse<ArmoryTidyDto> getArmory(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /armory - {}", name);
        return ApiResponse.ok(armoryTidyService.toTidy(name));
    }

    @GetMapping("/{name}/profile")
    public ApiResponse<ProfileTidyDto> getProfile(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /profile - {}", name);
        return ApiResponse.ok(armoryTidyService.getProfile(name));
    }

    @GetMapping("/{name}/equipment")
    public ApiResponse<List<EquipmentTidyDto>> getEquipment(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /equipment - {}", name);
        return ApiResponse.ok(armoryTidyService.getEquipment(name));
    }

    @GetMapping("/{name}/gems")
    public ApiResponse<GemTidyDto> getGems(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /gems - {}", name);
        return ApiResponse.ok(armoryTidyService.getGems(name));
    }

    @GetMapping("/{name}/engravings")
    public ApiResponse<EngravingTidyDto> getEngravings(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /engravings - {}", name);
        return ApiResponse.ok(armoryTidyService.getEngravings(name));
    }

    @GetMapping("/{name}/avatars")
    public ApiResponse<List<AvatarTidyDto>> getAvatars(@PathVariable String name) {
        return ApiResponse.ok(armoryTidyService.getAvatars(name));
    }

    @GetMapping("/{name}/combat-skills")
    public ApiResponse<List<CombatSkillTidyDto>> getCombatSkills(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /skills - {}", name);
        return ApiResponse.ok(armoryTidyService.getCombatSkills(name));
    }

    @GetMapping("/{name}/cards")
    public ApiResponse<CardTidyDto> getCards(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /cards - {}", name);
        return ApiResponse.ok(armoryTidyService.getCards(name));
    }

    @GetMapping("/{name}/collectibles")
    public ApiResponse<List<CollectibleTidyDto>> getCollectibles(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /collectibles - {}", name);
        return ApiResponse.ok(armoryTidyService.getCollectibles(name));
    }

    @GetMapping("/{name}/colosseum")
    public ApiResponse<ColosseumTidyDto> getColosseum(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /colosseum - {}", name);
        return ApiResponse.ok(armoryTidyService.getColosseum(name));
    }

    @GetMapping("/{name}/ark-passive")
    public ApiResponse<ArkPassiveTidyDto> getArkPassive(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /ark-passive - {}", name);
        return ApiResponse.ok(armoryTidyService.getArkPassive(name));
    }

    @GetMapping("/{name}/ark-grid")
    public ApiResponse<ArkGridTidyDto> getArkGrid(@PathVariable String name) {
        log.info("[ArmoryAPI] GET /ark-grid - {}", name);
        return ApiResponse.ok(armoryTidyService.getArkGrid(name));
    }
}
