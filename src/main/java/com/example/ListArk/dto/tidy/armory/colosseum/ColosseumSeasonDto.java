package com.example.ListArk.dto.tidy.armory.colosseum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Tidy 증명의 전장 시즌 정보 DTO")
public class ColosseumSeasonDto {

    @Schema(description = "시즌명", example = "시즌 3")
    private String seasonName;

    @Schema(description = "랭크 이름", example = "실버")
    private String rankName;

    @Schema(description = "랭크 아이콘 URL", example = "https://cdn.lostark.game/pvp/silver.png")
    private String rankIcon;

    @Schema(description = "승리 횟수", example = "24")
    private Integer win;

    @Schema(description = "패배 횟수", example = "18")
    private Integer lose;

    @Schema(description = "무승부 횟수", example = "3")
    private Integer tie;

    @Schema(description = "총 처치 수", example = "140")
    private Integer kill;

    @Schema(description = "총 사망 수", example = "102")
    private Integer death;

    @Schema(description = "에이스 횟수", example = "8")
    private Integer ace;
}
