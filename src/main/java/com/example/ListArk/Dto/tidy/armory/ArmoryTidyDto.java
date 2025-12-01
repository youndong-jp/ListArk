package com.example.ListArk.Dto.tidy.armory;

import com.example.ListArk.Dto.tidy.armory.arkgrid.ArkGridTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.Dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.Dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.Dto.tidy.armory.collectible.CollectibleTidyDto;
import lombok.Data;
import java.util.List;

@Data
public class ArmoryTidyDto {

    private ProfileTidyDto profile;
    private List<EquipmentTidyDto> equipment;
    private List<com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto> avatars;
    private List<CombatSkillTidyDto> skills;
    private EngravingTidyDto engravings;
    private CardTidyDto card;
    private List<GemTidyDto> gems;

    private List<CollectibleTidyDto> collectibles;
    private ArkPassiveTidyDto arkPassive;
    private ArkGridTidyDto arkGrid;
}
