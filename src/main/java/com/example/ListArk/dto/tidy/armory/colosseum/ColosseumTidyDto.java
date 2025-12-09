package com.example.ListArk.dto.tidy.armory.colosseum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 증명의 전장 전체 정보 DTO")
public class ColosseumTidyDto {

    @Schema(description = "현재 랭킹", example = "5421")
    private int rank;

    @Schema(description = "이전 시즌 랭킹", example = "6230")
    private int preRank;

    @Schema(description = "현재 경험치(EXP)", example = "24123")
    private int exp;

    @Schema(
            description = "시즌별 증명의 전장 기록 리스트",
            example = "[{\"seasonName\":\"시즌 3\",\"rankName\":\"실버\",\"win\":24,\"lose\":18}]"
    )
    private List<ColosseumSeasonDto> seasons;
}
