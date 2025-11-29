package com.example.ListArk.controller;

import com.example.ListArk.Dto.raw.character.SiblingCharacterDto;
import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.client.api.ArmoryClient;
import com.example.ListArk.client.api.CharacterClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class ApiCharacterController {

    private final CharacterClient characterClient;
    private final ArmoryClient apiClientService;

    @GetMapping("/{name}/siblings")
    public ApiResponse<List<SiblingCharacterDto>> getSiblings(@PathVariable String name) {
        List<SiblingCharacterDto> siblings = characterClient.getCharacterSiblings(name).block();
        return ApiResponse.ok(siblings);
    }

    @GetMapping("/{name}/armory-test")
    public ArmoryDto testArmory(@PathVariable String name) {
        return apiClientService.getCharacterArmory(name).block();
    }
    @GetMapping("/{name}/colosseum-test")
    public ArmoryColosseumDto testColosseum(@PathVariable String name) {
        return apiClientService.getCharacterColosseums(name).block();
    }
}
