package com.example.ListArk.Dto.raw.armory.gem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GemEffectDto {

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Skills")
    private List<GemEffectSkillDto> skills;
}
