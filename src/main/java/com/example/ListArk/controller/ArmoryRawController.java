package com.example.ListArk.controller;

import com.example.ListArk.client.raw.ArmoryRawClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/raw/characters")
@RequiredArgsConstructor
public class ArmoryRawController {

    private final ArmoryRawClient armoryRawClient;
    @GetMapping("/{name}/armory")
    public String getRawCharacterArmory(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArmory(name).block();
    }
    @GetMapping("/{name}/profiles")
    public String getRawProfile(@PathVariable String name) {
        return armoryRawClient.getRawCharacterProfile(name).block();
    }

    @GetMapping("/{name}/gems")
    public String getRawGems(@PathVariable String name) {
        return armoryRawClient.getRawCharacterGems(name).block();
    }

    @GetMapping("/{name}/stats")
    public String getRawStats(@PathVariable String name) {
        return armoryRawClient.getRawCharacterStats(name).block();
    }

    @GetMapping("/{name}/engravings")
    public String getRawEngravings(@PathVariable String name) {
        return armoryRawClient.getRawCharacterEngravings(name).block();
    }

    @GetMapping("/{name}/avatars")
    public String getRawAvatars(@PathVariable String name) {
        return armoryRawClient.getRawCharacterAvatars(name).block();
    }

    @GetMapping("/{name}/combat-skills")
    public String getRawCombatSkills(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCombatSkills(name).block();
    }

    @GetMapping("/{name}/cards")
    public String getRawCards(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCards(name).block();
    }

    @GetMapping("/{name}/colosseums")
    public String getRawColosseums(@PathVariable String name) {
        return armoryRawClient.getRawCharacterColosseums(name).block();
    }

    @GetMapping("/{name}/collectibles")
    public String getRawCollectibles(@PathVariable String name) {
        return armoryRawClient.getRawCharacterCollectibles(name).block();
    }

    @GetMapping("/{name}/arkpassive")
    public String getRawArkPassive(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArkPassive(name).block();
    }

    @GetMapping("/{name}/arkgrid")
    public String getRawArkGrid(@PathVariable String name) {
        return armoryRawClient.getRawCharacterArkGrid(name).block();
    }

}
