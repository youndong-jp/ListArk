package com.example.ListArk.controller;

import com.example.ListArk.Dto.character.CharacterProfileDto;
import com.example.ListArk.Dto.SiblingCharacterDto;
import com.example.ListArk.common.ApiResponse;
import com.example.ListArk.service.ApiClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class ApiCharacterController {

    private final ApiClientService apiClientService;

    public ApiCharacterController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping("/{name}/siblings")
    public ApiResponse<List<SiblingCharacterDto>> getSiblings(@PathVariable String name) {
        List<SiblingCharacterDto> siblings = apiClientService.getCharacterSiblings(name).block();
        return ApiResponse.ok(siblings);
    }

    @GetMapping("/{name}/profile")
    public ApiResponse<CharacterProfileDto> getProfile(@PathVariable String name) {
        CharacterProfileDto profile = apiClientService.getCharacterProfile(name).block();
        return ApiResponse.ok(profile);
    }
}