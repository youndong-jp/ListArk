package com.example.ListArk.Dto.raw.armory.colosseum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ArmoryColosseumDto {

    @JsonProperty("Rank")
    private int rank;

    @JsonProperty("PreRank")
    private int preRank;

    @JsonProperty("Exp")
    private int exp;

    @JsonProperty("Colosseums")
    private List<ColosseumEntryDto> colosseums;
}
