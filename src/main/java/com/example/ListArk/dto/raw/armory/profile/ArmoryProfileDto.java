package com.example.ListArk.dto.raw.armory.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArmoryProfileDto {

    @JsonProperty("CharacterImage")
    private String characterImage;

    @JsonProperty("ExpeditionLevel")
    private int expeditionLevel;

    @JsonProperty("PvpGradeName")
    private String pvpGradeName;

    @JsonProperty("TownLevel")
    private Integer townLevel; // null 가능하므로 Integer

    @JsonProperty("TownName")
    private String townName;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("GuildMemberGrade")
    private String guildMemberGrade;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("UsingSkillPoint")
    private int usingSkillPoint;

    @JsonProperty("TotalSkillPoint")
    private int totalSkillPoint;

    @JsonProperty("Stats")
    private List<StatDto> stats;

    @JsonProperty("Tendencies")
    private List<TendencyDto> tendencies;

    @JsonProperty("CombatPower")
    private String combatPower;

    @JsonProperty("Decorations")
    private DecorationDto decorations;

    @JsonProperty("HonorPoint")
    private int honorPoint;

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
