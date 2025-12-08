package com.example.ListArk.dto.raw.armory.colosseum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoOpBattleDto {

    @JsonProperty("PlayCount")
    private int playCount;

    @JsonProperty("VictoryCount")
    private int victoryCount;

    @JsonProperty("LoseCount")
    private int loseCount;

    @JsonProperty("TieCount")
    private int tieCount;

    @JsonProperty("KillCount")
    private int killCount;

    @JsonProperty("AceCount")
    private int aceCount;

    @JsonProperty("DeathCount")
    private int deathCount;
}
