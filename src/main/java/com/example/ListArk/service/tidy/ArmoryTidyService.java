package com.example.ListArk.service.tidy;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.tidy.armory.ArmoryTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkgrid.ArkGridTidyDto;
import com.example.ListArk.Dto.tidy.armory.arkpassive.ArkPassiveTidyDto;
import com.example.ListArk.Dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.Dto.tidy.armory.profile.ProfileTidyDto;
import com.example.ListArk.mapper.armory.*;
import com.example.ListArk.mapper.NullSafe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmoryTidyService {

    private final ProfileTidyMapper profileMapper;
    private final EquipmentTidyMapper equipmentMapper;
    private final AvatarTidyMapper avatarMapper;
    private final EngravingTidyMapper engravingMapper;
    private final GemTidyMapper gemMapper;
    private final CombatSkillTidyMapper combatSkillMapper;
    private final CardTidyMapper cardMapper;
    private final CollectiblesTidyMapper collectiblesMapper;
    private final ColosseumTidyMapper colosseumMapper;
    private final ArkPassiveTidyMapper arkPassiveMapper;
    private final ArkGridTidyMapper arkGridMapper;

    /**
     * ArmoryDto → ArmoryTidyDto 통합 변환
     * 모든 Mapper를 조합하여 완전한 Tidy 데이터 생성
     */
    public ArmoryTidyDto toTidy(ArmoryDto raw) {
        if (raw == null) {
            return empty();
        }

        ArmoryTidyDto dto = new ArmoryTidyDto();

        dto.setProfile(NullSafe.get(() -> profileMapper.toTidy(raw)));
        dto.setEquipment(NullSafe.list(equipmentMapper.toTidy(raw)));
        dto.setAvatars(NullSafe.list(avatarMapper.toTidy(raw)));
        dto.setEngravings(NullSafe.get(() -> engravingMapper.toTidy(raw)));
        dto.setGems(NullSafe.get(() -> gemMapper.toTidy(raw)));
        dto.setCombatSkills(NullSafe.list(combatSkillMapper.toTidy(raw)));
        dto.setCards(NullSafe.get(() -> cardMapper.toTidy(raw)));
        dto.setCollectibles(NullSafe.list(collectiblesMapper.toTidy(raw)));
        dto.setColosseum(NullSafe.get(() -> colosseumMapper.toTidy(raw)));
        dto.setArkPassive(NullSafe.get(() -> arkPassiveMapper.toTidy(raw)));
        dto.setArkGrid(NullSafe.get(() -> arkGridMapper.toTidy(raw)));

        return dto;
    }

    /**
     * Null 대응용 빈 ArmoryTidyDto
     */
    private ArmoryTidyDto empty() {
        ArmoryTidyDto dto = new ArmoryTidyDto();

        // ✅ 모든 객체는 빈 객체 반환
        dto.setProfile(new ProfileTidyDto());
        dto.setEngravings(new EngravingTidyDto());
        dto.setGems(new GemTidyDto());
        dto.setCards(new CardTidyDto());
        dto.setColosseum(new ColosseumTidyDto());
        dto.setArkPassive(new ArkPassiveTidyDto());
        dto.setArkGrid(new ArkGridTidyDto());

        // ✅ 모든 리스트는 빈 배열
        dto.setEquipment(List.of());
        dto.setAvatars(List.of());
        dto.setCombatSkills(List.of());
        dto.setCollectibles(List.of());

        return dto;
    }
}