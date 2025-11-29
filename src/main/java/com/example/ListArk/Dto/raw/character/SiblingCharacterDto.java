package com.example.ListArk.Dto.raw.character;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SiblingCharacterDto {

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterLevel")
    private int characterLevel;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("ItemAvgLevel")
    private String itemAvgLevel;
}
