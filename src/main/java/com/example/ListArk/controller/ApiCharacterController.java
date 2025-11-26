package com.example.ListArk.controller;

import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.Dto.character.ProfileDto;
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
    public ApiResponse<ProfileDto> getProfile(@PathVariable String name) {

        ProfileDto profile = characterService.getCharacterProfile(name);

        return ApiResponse.ok(profile);
    }
}
