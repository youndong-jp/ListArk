package com.example.ListArk.service;

import com.example.ListArk.Dto.character.ProfileDto;

public interface CharacterService {
    ProfileDto getCharacterProfile(String characterName);
}
