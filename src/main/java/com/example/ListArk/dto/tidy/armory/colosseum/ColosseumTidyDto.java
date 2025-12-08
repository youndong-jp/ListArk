package com.example.ListArk.dto.tidy.armory.colosseum;

import lombok.Data;
import java.util.List;

@Data
public class ColosseumTidyDto {

    private int rank;
    private int preRank;
    private int exp;

    private List<ColosseumSeasonDto> seasons;
}
