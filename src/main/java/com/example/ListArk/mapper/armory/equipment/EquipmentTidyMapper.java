package com.example.ListArk.mapper.armory.equipment;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Lost Ark API의 장비 데이터를 TidyDto로 변환하는 Mapper
 *
 * 주요 기능:
 * - ArmoryDto에서 장비 리스트 추출
 * - 각 장비의 기본 정보 매핑 (슬롯, 이름, 아이콘, 등급)
 * - Tooltip JSON 파싱하여 상세 정보 추출
 *
 * @see EquipmentTooltipParser tooltip 파싱 담당 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EquipmentTidyMapper {

    private final EquipmentTooltipParser tooltipParser;

    /**
     * ArmoryDto에서 장비 데이터를 추출하여 TidyDto 리스트로 변환
     *
     * @param armoryDto 원본 Armory 데이터
     * @return 변환된 장비 TidyDto 리스트
     */
    public List<EquipmentTidyDto> toTidy(ArmoryDto armoryDto) {
        if (armoryDto == null || armoryDto.getArmoryEquipment() == null) {
            log.warn("ArmoryDto 또는 장비 데이터가 null입니다.");
            return new ArrayList<>();
        }

        List<EquipmentDto> equipmentList = armoryDto.getArmoryEquipment();
        List<EquipmentTidyDto> result = new ArrayList<>();

        for (EquipmentDto equipment : equipmentList) {
            try {
                EquipmentTidyDto dto = new EquipmentTidyDto();

                // 기본 필드 매핑
                dto.setSlot(equipment.getType());
                dto.setName(equipment.getName());
                dto.setIcon(equipment.getIcon());
                dto.setGrade(equipment.getGrade());

                // Tooltip 파싱 (상세 정보 추출)
                if (equipment.getTooltip() != null && !equipment.getTooltip().isEmpty()) {
                    tooltipParser.parseAndSetTooltip(equipment.getTooltip(), dto);
                }

                result.add(dto);

            } catch (Exception e) {
                log.error("장비 파싱 중 에러 발생 - 슬롯: {}, 이름: {}, 에러: {}",
                        equipment.getType(), equipment.getName(), e.getMessage(), e);
                // 에러가 발생해도 다음 장비는 계속 처리
            }
        }

        log.debug("장비 매핑 완료 - 총 {}개", result.size());
        return result;
    }
}