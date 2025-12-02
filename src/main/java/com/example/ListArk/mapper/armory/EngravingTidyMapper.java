package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.engraving.ArmoryEngravingDto;
import com.example.ListArk.Dto.raw.armory.engraving.EngravingDto;
import com.example.ListArk.Dto.tidy.armory.engraving.EngravingTidyDto;
import com.example.ListArk.mapper.NullSafe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EngravingTidyMapper {

    private final ObjectMapper objectMapper;

    public EngravingTidyMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EngravingTidyDto toTidy(ArmoryDto raw) {
        ArmoryEngravingDto engravingRoot = NullSafe.get(raw::getArmoryEngraving);

        EngravingTidyDto dto = new EngravingTidyDto();

        if (engravingRoot == null) {
            dto.setEngravings(List.of());
            return dto;
        }

        List<EngravingDto> engravings = NullSafe.get(
                engravingRoot::getEngravings,
                List.of()
        );

        // 각 각인을 "이름 Lv.레벨" 형식의 문자열로 변환
        List<String> engravingTexts = engravings.stream()
                .map(this::convertToText)
                .filter(text -> !text.isEmpty())  // 빈 문자열 제거
                .collect(Collectors.toList());

        dto.setEngravings(engravingTexts);
        return dto;
    }

    /** 각인 DTO를 "원한 Lv.3" 형식의 문자열로 변환 */
    private String convertToText(EngravingDto e) {
        String name = NullSafe.get(e::getName, "");
        String tooltip = NullSafe.get(e::getTooltip, "");

        // Tooltip에서 레벨 정보 추출
        JsonNode root = parseTooltip(tooltip);
        int level = extractEngravingLevel(root, name);

        // "원한 Lv.3" 형식으로 반환
        if (name.isEmpty()) return "";
        return level > 0 ? name + " Lv." + level : name;
    }

    /** Tooltip JSON 안전 파싱 */
    private JsonNode parseTooltip(String tooltip) {
        try {
            return objectMapper.readTree(tooltip);
        } catch (Exception ex) {
            return null;
        }
    }

    /** 각인 레벨 추출 (Lv.3 → 3) */
    private int extractEngravingLevel(JsonNode root, String name) {
        if (root == null) return 0;

        // Tooltip에서 레벨 텍스트 추출
        String levelText = root.path("Element_002")
                .path("value")
                .path("Element_000")
                .asText("");

        // "원한 Lv.3" → 3 추출
        if (!levelText.isEmpty()) {
            int level = parseLevelFromText(levelText);
            if (level > 0) return level;
        }

        // Fallback: Name에서 직접 추출
        return parseLevelFromText(name);
    }

    /** "원한 Lv.3" 문자열에서 레벨(3)만 추출 */
    private int parseLevelFromText(String text) {
        if (text == null || text.isEmpty()) return 0;

        try {
            String digits = text.replaceAll("[^0-9]", "");
            return digits.isEmpty() ? 0 : Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}