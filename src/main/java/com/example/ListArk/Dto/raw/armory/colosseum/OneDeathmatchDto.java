package com.example.ListArk.Dto.raw.armory.colosseum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OneDeathmatchDto {

    @JsonProperty("KillCount")
    private int killCount;

    @JsonProperty("DeathCount")
    private int deathCount;

    @JsonProperty("AllKillCount")
    private int allKillCount;

    @JsonProperty("OutDamage")
    private int outDamage;

    @JsonProperty("InDamage")
    private int inDamage;

    @JsonProperty("FirstWinCount")
    private int firstWinCount;

    @JsonProperty("SecondWinCount")
    private int secondWinCount;

    @JsonProperty("ThirdWinCount")
    private int thirdWinCount;

    @JsonProperty("FirstPlayCount")
    private int firstPlayCount;

    @JsonProperty("SecondPlayCount")
    private int secondPlayCount;

    @JsonProperty("ThirdPlayCount")
    private int thirdPlayCount;
}
