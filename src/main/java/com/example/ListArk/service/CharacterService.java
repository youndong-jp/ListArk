package com.example.ListArk.service;

import com.example.ListArk.Dto.tidy.ProfileTidyDto;

public interface CharacterService {
    ProfileTidyDto getCharacterProfile(String characterName);
}
