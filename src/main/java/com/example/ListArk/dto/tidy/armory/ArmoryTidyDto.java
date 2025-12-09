package com.example.ListArk.dto.tidy.armory;

import com.example.ListArk.dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.dto.tidy.armory.collectible.CollectibleTidyDto;
import com.example.ListArk.dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.dto.tidy.armory.arkgrid.ArkGridTidyDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Armory 전체 정보를 통합한 Tidy DTO (프로필 / 장비 / 각인 / 아바타 / 보석 / 전투스킬 / 카드 등)")
public class ArmoryTidyDto {

    @Schema(description = "캐릭터 프로필 정보")
    private ProfileTidyDto profile;

    @Schema(description = "장비 정보 리스트 (무기/방어구/장신구/팔찌 등)", nullable = true)
    private List<EquipmentTidyDto> equipment;

    @Schema(description = "아바타 목록")
    private List<AvatarTidyDto> avatars;

    @Schema(description = "각인 정보")
    private EngravingTidyDto engravings;

    @Schema(description = "보석 정보")
    private GemTidyDto gems;

    @Schema(description = "전투 스킬 정보")
    private List<CombatSkillTidyDto> combatSkills;

    @Schema(description = "카드 장착 및 세트 효과 정보")
    private CardTidyDto cards;

    @Schema(description = "모험물 정보 (모코코 / 섬의 마음 / 오르페우스의 별 등)")
    private List<CollectibleTidyDto> collectibles;

    @Schema(description = "증명의 전장(콜로세움) PvP 정보")
    private ColosseumTidyDto colosseum;

    @Schema(description = "아크 패시브 활성도 및 효과")
    private ArkPassiveTidyDto arkPassive;

    @Schema(description = "아크 그리드 코어/젬/효과 전체 정보")
    private ArkGridTidyDto arkGrid;
}
