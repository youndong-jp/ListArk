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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AvatarTidyMapper
 *
 * <p>
 * 책임:
 * 1. Raw AvatarDto를 기반으로 Tidy DTO의 "기본 상태"를 생성한다.
 * 2. Tooltip JSON이 존재할 경우에만 AvatarTooltipParser에 위임하여
 *    추가 정보를 덮어쓴다.
 *
 * <p>
 * 설계 원칙:
 * - Mapper는 항상 "의미 있는 기본값"을 가진 DTO를 반환한다.
 * - Tooltip 파싱 실패/누락이 전체 매핑을 실패시키지 않는다.
 * - TooltipParser는 기본값을 설정하지 않고, override 역할만 수행한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AvatarTidyMapper {

    private final AvatarTooltipParser tooltipParser;
    private final ObjectMapper objectMapper;

    /**
     * ArmoryDto → List&lt;AvatarTidyDto&gt;
     *
     * @param raw Armory 원본 DTO
     * @return 변환된 Tidy 아바타 리스트 (null-safe)
     */
    public List<AvatarTidyDto> toTidy(ArmoryDto raw) {
        if (raw == null) {
            return List.of();
        }

        List<AvatarDto> avatars =
                NullSafe.list(NullSafe.get(raw::getArmoryAvatar, null));

        return avatars.stream()
                .map(this::convert)
                .toList();
    }

    /**
     * AvatarDto → AvatarTidyDto
     *
     * <p>
     * 처리 흐름:
     * 1. Raw DTO로부터 기본 필드 및 기본 상태를 세팅한다.
     * 2. Tooltip JSON이 유효한 경우에만 파싱을 시도한다.
     * 3. Tooltip 파싱 실패 시에도 기본 정보는 유지된다.
     */
    private AvatarTidyDto convert(AvatarDto raw) {
        AvatarTidyDto tidy = new AvatarTidyDto();

        // ----------------------------------------------------
        // 1. 기본 상태 세팅 (항상 보장되어야 하는 값들)
        // ----------------------------------------------------
        tidy.setEffects(new ArrayList<>());

        // 거래 관련 기본 플래그
        tidy.setIsTradable(true);
        tidy.setIsSellable(true);
        tidy.setIsDecomposable(true);
        tidy.setIsBound(false);

        // ----------------------------------------------------
        // 2. Raw DTO 필드 매핑
        // ----------------------------------------------------
        tidy.setType(NullSafe.get(raw::getType, ""));
        tidy.setName(NullSafe.get(raw::getName, ""));
        tidy.setIcon(NullSafe.get(raw::getIcon, ""));
        tidy.setGrade(NullSafe.get(raw::getGrade, ""));
        tidy.setIsSet(NullSafe.get(raw::isSet, false));
        tidy.setIsInner(NullSafe.get(raw::isInner, false));

        // ----------------------------------------------------
        // 3. Tooltip JSON 파싱 (있을 경우에만)
        // ----------------------------------------------------
        Object tooltipStr = NullSafe.get(raw::getTooltip, null);
        if (tooltipStr instanceof String jsonStr && !jsonStr.isEmpty()) {
            try {
                Map<String, Object> tooltipMap = objectMapper.readValue(
                        jsonStr,
                        new TypeReference<Map<String, Object>>() {}
                );

                // TooltipParser는 기본값을 덮어쓰는 역할만 수행
                tooltipParser.parseTooltipData(tooltipMap, tidy);

            } catch (Exception e) {
                log.warn("Avatar tooltip parsing failed: {}", e.getMessage());
            }
        }

        return tidy;
    }
}
