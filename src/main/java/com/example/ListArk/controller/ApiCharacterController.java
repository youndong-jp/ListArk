package com.example.ListArk.controller;

import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.Dto.raw.CharacterArmoryDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.tidy.ProfileTidyDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.service.ApiClientService;
import com.example.ListArk.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class ApiCharacterController {

    private final ApiClientService apiClientService;
    private final CharacterService characterService;

    @GetMapping("/{name}/siblings")
    public ApiResponse<List<SiblingCharacterDto>> getSiblings(@PathVariable String name) {
        List<SiblingCharacterDto> siblings = apiClientService.getCharacterSiblings(name).block();
        return ApiResponse.ok(siblings);
    }

    @GetMapping("/{name}/profile")
    public ApiResponse<ProfileTidyDto> getProfile(@PathVariable String name) {

        ProfileTidyDto profile = characterService.getCharacterProfile(name);

        return ApiResponse.ok(profile);
    }
    @GetMapping("/{name}/armory-test")
    public CharacterArmoryDto testArmory(@PathVariable String name) {
        return apiClientService.getCharacterArmory(name).block();
    }
    @GetMapping("/{name}/colosseum-test")
    public ArmoryColosseumDto testColosseum(@PathVariable String name) {
        return apiClientService.getCharacterColosseums(name).block();
    }
}
