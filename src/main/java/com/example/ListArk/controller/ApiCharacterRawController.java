package com.example.ListArk.controller;

import com.example.ListArk.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/raw/characters")
@RequiredArgsConstructor
public class ApiCharacterRawController {

    private final ApiClientService apiClientService;

    @GetMapping("/{name}/armory")
    public String getRawCharacterArmory(@PathVariable String name) {
        return apiClientService.getRawCharacterArmory(name).block();
    }
    @GetMapping("/{name}/profiles")
    public String getRawProfile(@PathVariable String name) {
        return apiClientService.getRawCharacterProfile(name).block();
    }

    @GetMapping("/{name}/gems")
    public String getRawGems(@PathVariable String name) {
        return apiClientService.getRawCharacterGems(name).block();
    }

    @GetMapping("/{name}/stats")
    public String getRawStats(@PathVariable String name) {
        return apiClientService.getRawCharacterStats(name).block();
    }

    @GetMapping("/{name}/engravings")
    public String getRawEngravings(@PathVariable String name) {
        return apiClientService.getRawCharacterEngravings(name).block();
    }

    @GetMapping("/{name}/avatars")
    public String getRawAvatars(@PathVariable String name) {
        return apiClientService.getRawCharacterAvatars(name).block();
    }
}
