package com.example.ListArk.Dto.raw.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArmoryProfileDto {

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("ItemAvgLevel")
    private String itemAvgLevel;

    @JsonProperty("ItemMaxLevel")
    private String itemMaxLevel;

    @JsonProperty("CharacterLevel")
    private int characterLevel;

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("PvpGradeName")
    private String pvpGradeName;

    @JsonProperty("TownName")
    private String townName;

    @JsonProperty("TownLevel")
    private int townLevel;

    @JsonProperty("Title")
    private String title;
}
