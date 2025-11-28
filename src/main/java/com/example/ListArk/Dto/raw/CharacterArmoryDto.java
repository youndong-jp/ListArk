package com.example.ListArk.Dto.raw;

import com.example.ListArk.Dto.raw.armory.*;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.raw.armory.card.CardDto;
import com.example.ListArk.Dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.Dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.profile.ArmoryProfileDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CharacterArmoryDto {

    @JsonProperty("ArmoryProfile")
    private ArmoryProfileDto armoryProfile;

    @JsonProperty("ArmoryEquipment")
    private List<EquipmentDto> armoryEquipment;

    @JsonProperty("ArmoryCard")
    private CardDto armoryCard;

    @JsonProperty("ArmoryEngraving")
    private ArmoryEngravingDto armoryEngraving;

    @JsonProperty("ArmoryGem")
    private ArmoryGemDto armoryGem;

    @JsonProperty("ArmorySkills")
    private List<TripodDto> armorySkills;
}
