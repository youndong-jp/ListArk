package com.example.ListArk.Dto.character;

import com.example.ListArk.Dto.armory.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CharacterProfileDto {

    @JsonProperty("ArmoryProfile")
    private ArmoryProfileDto armoryProfile;

    @JsonProperty("ArmoryEquipment")
    private List<EquipmentDto> armoryEquipment;

    @JsonProperty("ArmoryAvatar")
    private List<AvatarDto> armoryAvatar;

    @JsonProperty("ArmoryCard")
    private CardDto armoryCard;

    @JsonProperty("ArmoryEngraving")
    private EngravingDto armoryEngraving;

    @JsonProperty("ArmoryGem")
    private ArmoryGemDto ArmoryGem;

    @JsonProperty("ArmorySkills")
    private List<SkillDto> armorySkills;

    @JsonProperty("ArmoryStat")
    private List<StatDto> armoryStat;
}
