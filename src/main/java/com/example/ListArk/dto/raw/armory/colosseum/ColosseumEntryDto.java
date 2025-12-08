package com.example.ListArk.dto.raw.armory.colosseum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ColosseumEntryDto {

    @JsonProperty("SeasonName")
    private String seasonName;

    @JsonProperty("Competitive")
    private CompetitiveDto competitive;

    @JsonProperty("TeamDeathmatch")
    private TeamDeathmatchDto teamDeathmatch;

    @JsonProperty("TeamElimination")
    private TeamEliminationDto teamElimination;

    @JsonProperty("CoOpBattle")
    private CoOpBattleDto coOpBattle;

    @JsonProperty("OneDeathmatch")
    private OneDeathmatchDto oneDeathmatch;

    @JsonProperty("OneDeathmatchRank")
    private OneDeathmatchRankDto oneDeathmatchRank;
}
