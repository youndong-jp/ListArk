package com.example.ListArk.controller;

import com.example.ListArk.config.annotation.CommonErrorResponses;
import com.example.ListArk.dto.raw.character.SiblingCharacterDto;
import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.dto.common.ApiResponse;
import com.example.ListArk.client.api.ArmoryClient;
import com.example.ListArk.client.api.CharacterClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
@Tag(name = "캐릭터 API", description = "캐릭터 목록 / 형제 캐릭터 / 테스트용 Armory API 제공")
public class CharacterController {

    private final CharacterClient characterClient;
    private final ArmoryClient apiClientService;

    // ----------------------------------------
    // 1) 형제 캐릭터 조회
    // ----------------------------------------
    @Operation(
            summary = "원정대 캐릭터 조회",
            description = "해당 캐릭터의 같은 계정 내 다른 캐릭터 목록을 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "정상 조회",
            content = @Content(schema = @Schema(implementation = SiblingCharacterDto.class))
    )
    @CommonErrorResponses
    @GetMapping("/{name}/siblings")
    public ApiResponse<List<SiblingCharacterDto>> getSiblings(@PathVariable String name) {
        List<SiblingCharacterDto> siblings = characterClient.getCharacterSiblings(name).block();
        return ApiResponse.ok(siblings);

    }
}
