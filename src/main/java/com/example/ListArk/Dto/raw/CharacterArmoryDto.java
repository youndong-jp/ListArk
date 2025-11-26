package com.example.ListArk.Dto.raw;

import com.example.ListArk.Dto.raw.armory.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CharacterArmoryDto {

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
    private ArmoryGemDto armoryGem;

    @JsonProperty("ArmorySkills")
    private List<TripodDto> armorySkills;

    @JsonProperty("ArmoryStat")
    private List<StatDto> armoryStat;
}
