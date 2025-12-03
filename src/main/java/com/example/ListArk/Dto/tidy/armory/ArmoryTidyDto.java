package com.example.ListArk.Dto.tidy.armory;

import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.Dto.tidy.armory.combatskill.CombatSkillTidyDto;
import com.example.ListArk.Dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.Dto.tidy.armory.collectible.CollectibleTidyDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkgrid.ArkGridTidyDto;
import lombok.Data;

import java.util.List;

@Data
public class ArmoryTidyDto {

    // 1. 프로필
    private ProfileTidyDto profile;

    // 2. 장비 (6개)
    private List<EquipmentTidyDto> equipment;

    // 3. 아바타
    private List<AvatarTidyDto> avatars;

    // 4. 각인
    private EngravingTidyDto engravings;

    // 5. 보석
    private GemTidyDto gems;

    // 6. 전투 스킬
    private List<CombatSkillTidyDto> combatSkills;

    // 7. 카드
    private CardTidyDto cards;

    // 8. 수집품
    private List<CollectibleTidyDto> collectibles;

    // 9. 콜로세움
    private ColosseumTidyDto colosseum;

    // 10. 아크 패시브
    private ArkPassiveTidyDto arkPassive;

    // 11. 아크 그리드
    private ArkGridTidyDto arkGrid;
}