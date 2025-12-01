package com.example.ListArk.Dto.tidy.armory.combatskill;

import lombok.Data;
import java.util.List;

@Data
public class CombatSkillTidyDto {

    private String name;
    private int level;
    private String icon;

    private List<TripodTidyDto> tripods;
    private String rune;
}

