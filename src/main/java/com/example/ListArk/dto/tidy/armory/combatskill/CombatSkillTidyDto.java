package com.example.ListArk.dto.tidy.armory.combatskill;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 전투 스킬 정보 DTO")
public class CombatSkillTidyDto {

    @Schema(description = "스킬 이름", example = "문라이트 소닉")
    private String name;

    @Schema(description = "스킬 레벨", example = "12")
    private int level;

    @Schema(description = "스킬 아이콘 URL", example = "https://cdn.lostark.game/skill/sonic.png")
    private String icon;

    @Schema(
            description = "해당 스킬의 트라이포드 정보 리스트",
            example =
                    "[" +
                            "{\"tier\":1, \"slot\":1, \"name\":\"빠른 준비\", \"selected\":true}," +
                            "{\"tier\":2, \"slot\":1, \"name\":\"약점 포착\", \"selected\":true}," +
                            "{\"tier\":3, \"slot\":2, \"name\":\"침식된 일격\", \"selected\":true}" +
                            "]"
    )
    private List<TripodTidyDto> tripods;

    @Schema(
            description = "장착된 룬 정보",
            example = "질풍 (전설)"
    )
    private String rune;
}
