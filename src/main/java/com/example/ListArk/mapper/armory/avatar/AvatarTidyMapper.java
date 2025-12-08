package com.example.ListArk.mapper.armory.avatar;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.avatar.AvatarDto;
import com.example.ListArk.dto.tidy.armory.avatar.AvatarTidyDto;
import com.example.ListArk.mapper.NullSafe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 아바타 매퍼
 * 1. Raw DTO에서 기본 정보 가져오기
 * 2. Tooltip JSON String → Map 변환
 * 3. Tooltip 파싱해서 추가 정보 채우기
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarTidyMapper {

    private final AvatarTooltipParser tooltipParser;
    private final ObjectMapper objectMapper;

    /**
     * ArmoryDto → List<AvatarTidyDto>
     */
    public List<AvatarTidyDto> toTidy(ArmoryDto raw) {
        List<AvatarDto> avatars = NullSafe.list(NullSafe.get(raw::getArmoryAvatar, null));

        return avatars.stream()
                .map(this::convert)
                .toList();
    }

    /**
     * AvatarDto → AvatarTidyDto 변환
     *
     * Raw 데이터 우선, Tooltip으로 추가 정보 보강
     */
    private AvatarTidyDto convert(AvatarDto raw) {
        AvatarTidyDto tidy = new AvatarTidyDto();

        // 1️⃣ Raw DTO에서 기본 정보 가져오기
        tidy.setType(NullSafe.get(raw::getType, ""));
        tidy.setName(NullSafe.get(raw::getName, ""));
        tidy.setIcon(NullSafe.get(raw::getIcon, ""));
        tidy.setGrade(NullSafe.get(raw::getGrade, ""));
        tidy.setIsSet(NullSafe.get(raw::isSet, false));
        tidy.setIsInner(NullSafe.get(raw::isInner, false));

        // 2️⃣ Tooltip JSON String → Map 변환
        Object tooltipStr = NullSafe.get(raw::getTooltip, null);
        if (tooltipStr instanceof String jsonStr && !jsonStr.isEmpty()) {
            try {
                // JSON String을 Map으로 파싱
                Map<String, Object> tooltipMap = objectMapper.readValue(
                        jsonStr,
                        new TypeReference<Map<String, Object>>() {}
                );

                // 3️⃣ Tooltip 파싱해서 추가 정보 채우기
                tooltipParser.parseTooltipData(tooltipMap, tidy);

            } catch (Exception e) {
                log.warn("Tooltip 파싱 실패: {}", e.getMessage());
            }
        }

        return tidy;
    }
}