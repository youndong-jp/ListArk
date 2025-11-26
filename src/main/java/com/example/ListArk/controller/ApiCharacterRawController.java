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

    @GetMapping("/{name}/profiles")
    public Object getRawProfile(@PathVariable String name) {
        return apiClientService.getCharacterProfile(name).block();
    }

        @GetMapping("/{name}/gems")
        public Object getRawGems(@PathVariable String name) {
            return apiClientService.getCharacterGems(name).block();
        }

        @GetMapping("/{name}/stats")
        public Object getRawStats(@PathVariable String name) {
            return apiClientService.getCharacterStats(name).block();
        }

        @GetMapping("/{name}/engravings")
        public Object getRawEngravings(@PathVariable String name) {
            return apiClientService.getCharacterEngravings(name).block();
        }

        @GetMapping("/{name}/avatars")
        public Object getRawAvatars(@PathVariable String name) {
            return apiClientService.getCharacterAvatars(name).block();
        }
    }
