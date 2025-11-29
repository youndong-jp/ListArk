package com.example.ListArk.Dto.raw;

import com.example.ListArk.Dto.raw.armory.arkgrid.ArmoryArkGridDto;
import com.example.ListArk.Dto.raw.armory.arkpassive.ArmoryArkPassiveDto;
import com.example.ListArk.Dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.Dto.raw.armory.card.ArmoryCardDto;
import com.example.ListArk.Dto.raw.armory.collectibles.CollectibleDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.raw.armory.combatskill.CombatSkillDto;
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

    @JsonProperty("ArmoryAvatars")
    private AvatarDto armoryAvatar;

    @JsonProperty("ArmorySkills")
    private List<CombatSkillDto> armorySkills;

    @JsonProperty("ArmoryEngraving")
    private ArmoryEngravingDto armoryEngraving;

    @JsonProperty("ArmoryCard")
    private ArmoryCardDto armoryCard;

    @JsonProperty("ArmoryGem")
    private ArmoryGemDto armoryGem;

    @JsonProperty("Colosseum")
    private ArmoryColosseumDto colosseum;

    @JsonProperty("ArmoryCollectibles")
    private List<CollectibleDto> armoryCollectibles;

    @JsonProperty("ArmoryArkPassive")
    private ArmoryArkPassiveDto armoryArkPassive;

    @JsonProperty("ArmoryArkGrid")
    private ArmoryArkGridDto armoryArkGrid;

}
