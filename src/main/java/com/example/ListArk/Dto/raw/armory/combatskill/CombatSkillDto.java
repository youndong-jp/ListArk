package com.example.ListArk.Dto.raw.armory.combatskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CombatSkillDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Level")
    private int level;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("SkillType")
    private int skillType;

    @JsonProperty("Tripods")
    private List<TripodDto> tripods;

    @JsonProperty("Rune")
    private RuneDto rune;

    @JsonProperty("Tooltip")
    private String tooltip;
}
